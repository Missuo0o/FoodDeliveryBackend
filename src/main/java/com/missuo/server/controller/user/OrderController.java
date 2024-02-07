package com.missuo.server.controller.user;

import com.missuo.common.result.Result;
import com.missuo.pojo.dto.OrdersSubmitDTO;
import com.missuo.pojo.vo.OrderSubmitVO;
import com.missuo.server.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Tag(name = "Order Management")
@Slf4j
public class OrderController {
  @Autowired private OrderService orderService;

  @PostMapping("/submit")
  public Result submit(@Validated @RequestBody OrdersSubmitDTO ordersSubmitDTO) {
    log.info("Submit order: {}", ordersSubmitDTO);
    OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
    return Result.success(orderSubmitVO);
  }
}
