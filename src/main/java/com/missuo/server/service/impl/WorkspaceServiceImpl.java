package com.missuo.server.service.impl;

import com.missuo.common.constant.StatusConstant;
import com.missuo.pojo.entity.Orders;
import com.missuo.pojo.vo.BusinessDataVO;
import com.missuo.pojo.vo.DishOverViewVO;
import com.missuo.pojo.vo.OrderOverViewVO;
import com.missuo.pojo.vo.SetmealOverViewVO;
import com.missuo.server.mapper.DishMapper;
import com.missuo.server.mapper.OrderMapper;
import com.missuo.server.mapper.SetmealMapper;
import com.missuo.server.mapper.UserMapper;
import com.missuo.server.service.WorkspaceService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

  @Autowired private OrderMapper orderMapper;
  @Autowired private UserMapper userMapper;
  @Autowired private DishMapper dishMapper;
  @Autowired private SetmealMapper setmealMapper;

  public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
    Map<String, Object> map = new HashMap<>();

    map.put("beginTime", begin);
    map.put("endTime", end);

    Integer totalOrderCount = orderMapper.countByMap(map);

    map.put("status", Orders.COMPLETED);
    BigDecimal turnover = orderMapper.sumByMap(map);
    turnover = turnover == null ? BigDecimal.ZERO : turnover;

    Integer validOrderCount = orderMapper.countByMap(map);

    Double orderCompletionRate =
        totalOrderCount == 0 ? 0.0 : validOrderCount.doubleValue() / totalOrderCount;

    BigDecimal unitPrice =
        totalOrderCount == 0
            ? BigDecimal.ZERO
            : turnover.divide(BigDecimal.valueOf(totalOrderCount), 2, RoundingMode.HALF_UP);

    Integer newUsers = userMapper.countByMap(map);

    return BusinessDataVO.builder()
        .turnover(turnover)
        .validOrderCount(validOrderCount)
        .orderCompletionRate(orderCompletionRate)
        .unitPrice(unitPrice)
        .newUsers(newUsers)
        .build();
  }

  public OrderOverViewVO getOrderOverView() {
    Map<String, Object> map = new HashMap<>();
    map.put("beginTime", LocalDateTime.now().with(LocalTime.MIN));
    map.put("endTime", LocalDateTime.now().with(LocalTime.MAX));
    map.put("status", Orders.TO_BE_CONFIRMED);

    Integer waitingOrders = orderMapper.countByMap(map);

    map.put("status", Orders.CONFIRMED);
    Integer deliveredOrders = orderMapper.countByMap(map);

    map.put("status", Orders.COMPLETED);
    Integer completedOrders = orderMapper.countByMap(map);

    map.put("status", Orders.CANCELLED);
    Integer cancelledOrders = orderMapper.countByMap(map);

    map.put("status", null);
    Integer allOrders = orderMapper.countByMap(map);

    return OrderOverViewVO.builder()
        .waitingOrders(waitingOrders)
        .deliveredOrders(deliveredOrders)
        .completedOrders(completedOrders)
        .cancelledOrders(cancelledOrders)
        .allOrders(allOrders)
        .build();
  }

  public DishOverViewVO getDishOverView() {
    Map<String, Object> map = new HashMap<>();
    map.put("status", StatusConstant.ENABLE);
    Integer sold = dishMapper.countByMap(map);

    map.put("status", StatusConstant.DISABLE);
    Integer discontinued = dishMapper.countByMap(map);

    return DishOverViewVO.builder().sold(sold).discontinued(discontinued).build();
  }

  public SetmealOverViewVO getSetmealOverView() {
    Map<String, Object> map = new HashMap<>();
    map.put("status", StatusConstant.ENABLE);
    Integer sold = setmealMapper.countByMap(map);

    map.put("status", StatusConstant.DISABLE);
    Integer discontinued = setmealMapper.countByMap(map);

    return SetmealOverViewVO.builder().sold(sold).discontinued(discontinued).build();
  }
}
