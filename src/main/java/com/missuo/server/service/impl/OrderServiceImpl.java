package com.missuo.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.missuo.common.constant.MessageConstant;
import com.missuo.common.context.BaseContext;
import com.missuo.common.exception.AddressBookBusinessException;
import com.missuo.common.exception.OrderBusinessException;
import com.missuo.common.exception.ShoppingCartBusinessException;
import com.missuo.common.utils.WeChatPayUtil;
import com.missuo.pojo.dto.OrdersPaymentDTO;
import com.missuo.pojo.dto.OrdersSubmitDTO;
import com.missuo.pojo.entity.*;
import com.missuo.pojo.vo.OrderPaymentVO;
import com.missuo.pojo.vo.OrderSubmitVO;
import com.missuo.server.mapper.*;
import com.missuo.server.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
  @Autowired private OrderMapper orderMapper;
  @Autowired private OrderDetailMapper orderDetailMapper;
  @Autowired private AddressBookMapper addressBookMapper;
  @Autowired private ShoppingCartMapper shoppingCartMapper;
  @Autowired private WeChatPayUtil weChatPayUtil;
  @Autowired private UserMapper userMapper;

  @Override
  @Transactional
  public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
    // Check if the address book exists
    AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
    if (addressBook == null) {
      throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
    }

    // Check if the shopping cart is empty
    Long currentId = BaseContext.getCurrentId();
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

    // Return the order information
    return OrderSubmitVO.builder()
        .id(orders.getId())
        .orderTime(orders.getOrderTime())
        .orderNumber(orders.getNumber())
        .orderAmount(orders.getAmount())
        .build();
  }

  public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
    // Get the user's openid
    Long userId = BaseContext.getCurrentId();
    User user = userMapper.getById(userId);

    BigDecimal amount = orderMapper.getByNumber(ordersPaymentDTO.getOrderNumber()).getAmount();

    // Call the WeChat payment interface
    JSONObject jsonObject =
        weChatPayUtil.pay(ordersPaymentDTO.getOrderNumber(), amount, "Missuo", user.getOpenid());

    if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
      throw new OrderBusinessException("This order has been paid");
    }

    OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
    vo.setPackageStr(jsonObject.getString("package"));

    return vo;
  }

  public void paySuccess(String outTradeNo) {
    // Update order status
    Orders ordersDB = orderMapper.getByNumber(outTradeNo);

    // If the order status is not paid, update the order status
    Orders orders =
        Orders.builder()
            .id(ordersDB.getId())
            .status(Orders.TO_BE_CONFIRMED)
            .payStatus(Orders.PAID)
            .checkoutTime(LocalDateTime.now())
            .build();

    orderMapper.update(orders);
  }
}
