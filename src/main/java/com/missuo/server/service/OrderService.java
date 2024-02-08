package com.missuo.server.service;

import com.missuo.pojo.dto.OrdersPaymentDTO;
import com.missuo.pojo.dto.OrdersSubmitDTO;
import com.missuo.pojo.vo.OrderPaymentVO;
import com.missuo.pojo.vo.OrderSubmitVO;

public interface OrderService {
  OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

  OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

  void paySuccess(String outTradeNo);
}
