package com.missuo.server.service;

import com.missuo.common.result.PageResult;
import com.missuo.pojo.dto.OrdersPaymentDTO;
import com.missuo.pojo.dto.OrdersSubmitDTO;
import com.missuo.pojo.vo.OrderPaymentVO;
import com.missuo.pojo.vo.OrderSubmitVO;
import com.missuo.pojo.vo.OrderVO;

public interface OrderService {
  OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

  OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

  void paySuccess(String outTradeNo);

  PageResult<OrderVO> pageQuery(int page, int pageSize, Integer status);
}
