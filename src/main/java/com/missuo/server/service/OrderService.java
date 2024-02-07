package com.missuo.server.service;

import com.missuo.pojo.dto.OrdersSubmitDTO;
import com.missuo.pojo.vo.OrderSubmitVO;

public interface OrderService {
  OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
