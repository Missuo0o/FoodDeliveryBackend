package com.missuo.server.service;

import com.missuo.common.result.PageResult;
import com.missuo.pojo.dto.*;
import com.missuo.pojo.vo.OrderPaymentVO;
import com.missuo.pojo.vo.OrderStatisticsVO;
import com.missuo.pojo.vo.OrderSubmitVO;
import com.missuo.pojo.vo.OrderVO;

public interface OrderService {
  OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

  OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

  void paySuccess(String outTradeNo);

  PageResult<OrderVO> pageQuery(int page, int pageSize, Integer status);

  OrderVO details(Long id);

  void userCancelById(Long id) throws Exception;

  void repetition(Long id);

  PageResult<OrderVO> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

  OrderStatisticsVO statistics();

  void confirm(OrdersConfirmDTO ordersConfirmDTO);

  void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

  void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

  void delivery(Long id);

  void complete(Long id);

  void reminder(Long id);
}
