package com.missuo.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.missuo.common.constant.MessageConstant;
import com.missuo.common.constant.MqConstant;
import com.missuo.common.context.BaseContext;
import com.missuo.common.exception.AddressBookBusinessException;
import com.missuo.common.exception.OrderBusinessException;
import com.missuo.common.exception.ShoppingCartBusinessException;
import com.missuo.common.result.PageResult;
import com.missuo.common.utils.WeChatPayUtil;
import com.missuo.pojo.dto.*;
import com.missuo.pojo.entity.*;
import com.missuo.pojo.message.MultDelayMessage;
import com.missuo.pojo.vo.OrderPaymentVO;
import com.missuo.pojo.vo.OrderStatisticsVO;
import com.missuo.pojo.vo.OrderSubmitVO;
import com.missuo.pojo.vo.OrderVO;
import com.missuo.server.mapper.*;
import com.missuo.server.service.OrderService;
import com.missuo.server.websocket.WebSocketServer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderMapper orderMapper;
  private final OrderDetailMapper orderDetailMapper;
  private final AddressBookMapper addressBookMapper;
  private final ShoppingCartMapper shoppingCartMapper;
  private final WeChatPayUtil weChatPayUtil;
  private final UserMapper userMapper;
  private final WebSocketServer webSocketServer;
  private final RabbitTemplate rabbitTemplate;

  @Override
  public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
    // Check if the address book exists
    AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
    if (addressBook == null) {
      throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
    }

    Long currentId = BaseContext.getCurrentId();

    Orders orders;
    synchronized (currentId.toString().intern()) {
      OrderService proxy = (OrderService) AopContext.currentProxy();
      orders = proxy.getOrders(ordersSubmitDTO, addressBook, currentId);
    }
    // Send a delayed message to the queue
    MultDelayMessage<Long> longMultDelayMessage = new MultDelayMessage<>();
    longMultDelayMessage.setDelayMillis(
        Stream.generate(() -> 60000L).limit(15).collect(Collectors.toList()));
    longMultDelayMessage.setData(orders.getId());

    rabbitTemplate.convertAndSend(
        MqConstant.DELAY_EXCHANGE,
        MqConstant.DELAY_ORDER_ROUTING_KEY,
        longMultDelayMessage,
        message -> {
          message.getMessageProperties().setDelayLong(longMultDelayMessage.removeNextDelay());
          return message;
        });

    // Return the order information
    return OrderSubmitVO.builder()
        .id(orders.getId())
        .orderTime(orders.getOrderTime())
        .orderNumber(orders.getNumber())
        .orderAmount(orders.getAmount())
        .build();
  }

  @Transactional
  public Orders getOrders(
      OrdersSubmitDTO ordersSubmitDTO, AddressBook addressBook, Long currentId) {
    // Check if the shopping cart is empty
    ShoppingCart shoppingCart = ShoppingCart.builder().id(currentId).build();
    List<ShoppingCart> shoppingCarts = shoppingCartMapper.list(shoppingCart);

    if (shoppingCarts.isEmpty()) {
      throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
    }

    // Create an order
    Orders orders = new Orders();
    BeanUtils.copyProperties(ordersSubmitDTO, orders);

    orders.setOrderTime(LocalDateTime.now());
    orders.setPayStatus(Orders.UN_PAID);
    orders.setStatus(Orders.PENDING_PAYMENT);
    orders.setNumber(System.currentTimeMillis() + "");
    orders.setPhone(addressBook.getPhone());
    orders.setConsignee(addressBook.getConsignee());
    orders.setUserId(currentId);
    orders.setAddress(addressBook.getDetail());

    orderMapper.insert(orders);

    // Create order details
    List<OrderDetail> orderDetails =
        shoppingCarts.stream()
            .map(
                cart -> {
                  OrderDetail orderDetail = new OrderDetail();
                  BeanUtils.copyProperties(cart, orderDetail);
                  orderDetail.setOrderId(orders.getId());
                  return orderDetail;
                })
            .toList();

    orderDetailMapper.insertBatch(orderDetails);

    // Delete the shopping cart
    shoppingCartMapper.deleteByUserId(currentId);
    return orders;
  }

  public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
    // Get the user's openid
    Long userId = BaseContext.getCurrentId();
    User user = userMapper.getById(userId);

    Orders orders = orderMapper.getByNumber(ordersPaymentDTO.getOrderNumber());

    if (orders == null) {
      throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
    }

    // Call the WeChat payment interface
    JSONObject jsonObject =
        weChatPayUtil.pay(
            ordersPaymentDTO.getOrderNumber(), orders.getAmount(), "Missuo", user.getOpenid());

    if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
      throw new OrderBusinessException("This order has been paid");
    }

    OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
    vo.setPackageStr(jsonObject.getString("package"));

    return vo;
  }

  public void paySuccess(String outTradeNo) {
    Orders ordersDB = orderMapper.getByNumber(outTradeNo);

    if (ordersDB == null) {
      throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
    }

    Orders orders =
        Orders.builder()
            .id(ordersDB.getId())
            .status(Orders.TO_BE_CONFIRMED)
            .payStatus(Orders.PAID)
            .checkoutTime(LocalDateTime.now())
            .build();

    orderMapper.update(orders);

    Map<String, Object> map = new HashMap<>();
    map.put("type", 1); // 1:New order 2:Reminder
    map.put("orderId", ordersDB.getId());
    map.put("orderNumber", "Order Number:" + ordersDB.getNumber());

    String jsonString = JSON.toJSONString(map);
    webSocketServer.sendToAllClient(jsonString);
  }

  @Override
  public PageResult<OrderVO> pageQuery(int page, int pageSize, Integer status) {
    try {
      PageHelper.startPage(page, pageSize);

      OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
      ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());
      ordersPageQueryDTO.setStatus(status);
      Page<Orders> pages = orderMapper.pageQuery(ordersPageQueryDTO);

      List<OrderVO> list =
          pages.stream()
              .map(
                  orders -> {
                    Long orderId = orders.getId();
                    List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(orderId);

                    OrderVO orderVO = new OrderVO();
                    BeanUtils.copyProperties(orders, orderVO);
                    orderVO.setOrderDetailList(orderDetails);

                    return orderVO;
                  })
              .collect(Collectors.toList());

      return new PageResult<>(pages.getTotal(), list);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public OrderVO details(Long id) {
    // Get the order information
    Orders orders = orderMapper.getById(id);
    if (orders == null) {
      throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
    }
    // Get the order details
    List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(orders.getId());

    OrderVO orderVO = new OrderVO();
    BeanUtils.copyProperties(orders, orderVO);
    orderVO.setOrderDetailList(orderDetails);
    return orderVO;
  }

  @Override
  public void userCancelById(Long id) throws Exception {
    // Get the order information
    Orders orders = orderMapper.getById(id);

    //    Verify whether the order exists
    if (orders == null) {
      throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
    }

    // Order status 1 Pending payment 2 Pending order 3 Received order 4 Delivery 5 Completed 6
    // Canceled //Order status 1 Pending payment 2 Pending order 3 Order received 4 Delivery 5
    // Completed 6 Canceled
    if (orders.getStatus() > 2) {
      throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
    }

    Orders order = new Orders();
    order.setId(orders.getId());

    if (Objects.equals(orders.getStatus(), Orders.TO_BE_CONFIRMED)) {
      // If the order status is to be confirmed, the order status is changed to canceled
      weChatPayUtil.refund(
          orders.getNumber(), orders.getNumber(), orders.getAmount(), orders.getAmount());

      orders.setPayStatus(Orders.REFUND);
    }
    orders.setStatus(Orders.CANCELLED);
    orders.setCancelReason("User cancellation");
    orders.setCancelTime(LocalDateTime.now());
    orderMapper.update(orders);
  }

  @Override
  public void repetition(Long id) {
    Long userId = BaseContext.getCurrentId();
    List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);

    List<ShoppingCart> shoppingCartList =
        orderDetailList.stream()
            .map(
                order -> {
                  ShoppingCart shoppingCart = new ShoppingCart();

                  BeanUtils.copyProperties(order, shoppingCart, "id");
                  shoppingCart.setUserId(userId);
                  shoppingCart.setCreateTime(LocalDateTime.now());

                  return shoppingCart;
                })
            .toList();

    shoppingCartMapper.insertBatch(shoppingCartList);
  }

  @Override
  public PageResult<OrderVO> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
    PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
    Page<Orders> page = orderMapper.pageQuery(ordersPageQueryDTO);

    List<OrderVO> orderVOList =
        page.getResult().stream()
            .map(
                orders -> {
                  OrderVO orderVO = new OrderVO();
                  BeanUtils.copyProperties(orders, orderVO);
                  orderVO.setOrderDishes(getOrderDishesStr(orders));
                  return orderVO;
                })
            .toList();

    return new PageResult<>(page.getTotal(), orderVOList);
  }

  @Override
  public OrderStatisticsVO statistics() {
    Integer toBeConfirmed = orderMapper.countStatus(Orders.TO_BE_CONFIRMED);
    Integer confirmed = orderMapper.countStatus(Orders.CONFIRMED);
    Integer deliveryInProgress = orderMapper.countStatus(Orders.DELIVERY_IN_PROGRESS);

    OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
    orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
    orderStatisticsVO.setConfirmed(confirmed);
    orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);
    return orderStatisticsVO;
  }

  @Override
  public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
    Orders orders = Orders.builder().id(ordersConfirmDTO.getId()).status(Orders.CONFIRMED).build();

    orderMapper.update(orders);
  }

  @Override
  public void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception {
    Orders orders = orderMapper.getById(ordersRejectionDTO.getId());
    //    The order can only be rejected if it exists and has a status of 2 (order pending).
    if (orders == null || !Orders.TO_BE_CONFIRMED.equals(orders.getStatus())) {
      throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
    }

    // User paid, refund required
    weChatPayUtil.refund(
        orders.getNumber(), orders.getNumber(), orders.getAmount(), orders.getAmount());

    Orders order = new Orders();
    order.setId(orders.getId());
    order.setStatus(Orders.CANCELLED);
    order.setRejectionReason(ordersRejectionDTO.getRejectionReason());
    order.setCancelTime(LocalDateTime.now());

    orderMapper.update(order);
  }

  @Override
  public void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception {
    Orders orders = orderMapper.getById(ordersCancelDTO.getId());
    if (orders == null) {
      throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
    }

    if (Objects.equals(orders.getPayStatus(), Orders.PAID)) {
      weChatPayUtil.refund(
          orders.getNumber(), orders.getNumber(), orders.getAmount(), orders.getAmount());
    }

    Orders order = new Orders();
    order.setId(ordersCancelDTO.getId());
    order.setStatus(Orders.CANCELLED);
    order.setCancelReason(ordersCancelDTO.getCancelReason());
    order.setCancelTime(LocalDateTime.now());
    orderMapper.update(order);
  }

  @Override
  public void delivery(Long id) {
    Orders orders = orderMapper.getById(id);

    if (orders == null || !Orders.CONFIRMED.equals(orders.getStatus())) {
      throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
    }
    Orders order = new Orders();
    order.setId(orders.getId());
    order.setStatus(Orders.DELIVERY_IN_PROGRESS);

    orderMapper.update(order);
  }

  @Override
  public void complete(Long id) {
    Orders orders = orderMapper.getById(id);

    if (orders == null || !Orders.DELIVERY_IN_PROGRESS.equals(orders.getStatus())) {
      throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
    }

    Orders order = new Orders();
    order.setId(orders.getId());
    order.setStatus(Orders.COMPLETED);
    order.setDeliveryTime(LocalDateTime.now());

    orderMapper.update(order);
  }

  @Override
  public void reminder(Long id) {
    Orders orders = orderMapper.getById(id);

    if (orders == null || !Orders.TO_BE_CONFIRMED.equals(orders.getStatus())) {
      throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
    }

    Map<String, Object> map = new HashMap<>();
    map.put("type", 2); // 1:New order 2:Reminder
    map.put("orderId", orders.getId());
    map.put("content", "Order Number:" + orders.getNumber());

    webSocketServer.sendToAllClient(JSON.toJSONString(map));
  }

  private String getOrderDishesStr(Orders orders) {
    // Get the order details
    List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());

    // Splicing order dishes
    List<String> orderDishList =
        orderDetailList.stream()
            .map(orderDetail -> orderDetail.getName() + "*" + orderDetail.getNumber() + ";")
            .toList();

    return String.join("", orderDishList);
  }
}
