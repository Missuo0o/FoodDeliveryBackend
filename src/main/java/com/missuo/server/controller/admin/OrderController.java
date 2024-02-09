package com.missuo.server.controller.admin;

import com.missuo.common.result.PageResult;
import com.missuo.common.result.Result;
import com.missuo.pojo.dto.OrdersCancelDTO;
import com.missuo.pojo.dto.OrdersConfirmDTO;
import com.missuo.pojo.dto.OrdersPageQueryDTO;
import com.missuo.pojo.dto.OrdersRejectionDTO;
import com.missuo.pojo.vo.OrderStatisticsVO;
import com.missuo.pojo.vo.OrderVO;
import com.missuo.server.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Slf4j
@Tag(name = "Order Management")
public class OrderController {
  @Autowired private OrderService orderService;

  @GetMapping("/conditionSearch")
  @Operation(summary = "Order Search")
  public Result conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
    log.info("ordersPageQueryDTO: {}", ordersPageQueryDTO);
    PageResult<OrderVO> pageResult = orderService.conditionSearch(ordersPageQueryDTO);
    return Result.success(pageResult);
  }

  @GetMapping("/statistics")
  @Operation(summary = "Order Statistics")
  public Result statistics() {
    log.info("statistics");
    OrderStatisticsVO orderStatisticsVO = orderService.statistics();
    return Result.success(orderStatisticsVO);
  }

  @GetMapping("/details/{id}")
  @Operation(summary = "Order Details")
  public Result details(@PathVariable Long id) {
    log.info("id: {}", id);
    OrderVO orderVO = orderService.details(id);
    return Result.success(orderVO);
  }

  @PutMapping("/confirm")
  @Operation(summary = "Order Confirmation")
  public Result confirm(@Validated @RequestBody OrdersConfirmDTO ordersConfirmDTO) {
    log.info("ordersConfirmDTO: {}", ordersConfirmDTO);
    orderService.confirm(ordersConfirmDTO);
    return Result.success();
  }

  @PutMapping("/rejection")
  @Operation(summary = "Order Rejection")
  public Result rejection(@Validated @RequestBody OrdersRejectionDTO ordersRejectionDTO)
      throws Exception {
    log.info("ordersRejectionDTO: {}", ordersRejectionDTO);
    orderService.rejection(ordersRejectionDTO);
    return Result.success();
  }

  @PutMapping("/cancel")
  @Operation(summary = "Order Cancellation")
  public Result cancel(@Validated @RequestBody OrdersCancelDTO ordersCancelDTO) throws Exception {
    log.info("ordersCancelDTO: {}", ordersCancelDTO);
    orderService.cancel(ordersCancelDTO);
    return Result.success();
  }

  @PutMapping("/delivery/{id}")
  @Operation(summary = "Order Delivery")
  public Result delivery(@PathVariable Long id) {
    log.info("id: {}", id);
    orderService.delivery(id);
    return Result.success();
  }

  @PutMapping("/complete/{id}")
  @Operation(summary = "Order Completion")
  public Result complete(@PathVariable Long id) {
    log.info("id: {}", id);
    orderService.complete(id);
    return Result.success();
  }
}
