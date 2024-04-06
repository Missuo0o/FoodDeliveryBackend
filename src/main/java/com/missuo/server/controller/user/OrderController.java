package com.missuo.server.controller.user;

import com.missuo.common.result.PageResult;
import com.missuo.common.result.Result;
import com.missuo.pojo.dto.OrdersPaymentDTO;
import com.missuo.pojo.dto.OrdersSubmitDTO;
import com.missuo.pojo.vo.OrderPaymentVO;
import com.missuo.pojo.vo.OrderSubmitVO;
import com.missuo.pojo.vo.OrderVO;
import com.missuo.server.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Tag(name = "Order Management")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
  private final OrderService orderService;

  @PostMapping("/submit")
  @Operation(summary = "submit order")
  public Result submit(@Validated @RequestBody OrdersSubmitDTO ordersSubmitDTO) {
    log.info("Submit order: {}", ordersSubmitDTO);
    OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
    return Result.success(orderSubmitVO);
  }

  @PutMapping("/payment")
  @Operation(summary = "payment")
  public Result payment(@Validated @RequestBody OrdersPaymentDTO ordersPaymentDTO)
      throws Exception {
    log.info("Payment: {}", ordersPaymentDTO);
    OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
    return Result.success(orderPaymentVO);
  }

  @GetMapping("/historyOrders")
  @Operation(summary = "history orders")
  public Result page(int page, int pageSize, Integer status) {
    log.info("History orders");
    PageResult<OrderVO> pageResult = orderService.pageQuery(page, pageSize, status);
    return Result.success(pageResult);
  }

  @GetMapping("/orderDetail/{id}")
  @Operation(summary = "Get Order Detail")
  public Result details(@PathVariable Long id) {
    log.info("Get order detail: {}", id);
    OrderVO orderVO = orderService.details(id);
    return Result.success(orderVO);
  }

  @PutMapping("/cancel/{id}")
  @Operation(summary = "Cancel Order")
  public Result cancel(@PathVariable Long id) throws Exception {
    log.info("Cancel order: {}", id);
    orderService.userCancelById(id);
    return Result.success();
  }

  @PostMapping("/repetition/{id}")
  @Operation(summary = "Repetition")
  public Result repetition(@PathVariable Long id) {
    log.info("Repetition: {}", id);
    orderService.repetition(id);
    return Result.success();
  }

  @GetMapping("/reminder/{id}")
  @Operation(summary = "Reminder")
  public Result reminder(@PathVariable Long id) {
    orderService.reminder(id);
    return Result.success();
  }
}
