package com.missuo.server.controller.admin;

import com.missuo.common.result.Result;
import com.missuo.pojo.vo.BusinessDataVO;
import com.missuo.server.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/workspace")
@Tag(name = "WorkSpace Management")
@RequiredArgsConstructor
@Slf4j
public class WorkSpaceController {

  private final WorkspaceService workspaceService;

  @GetMapping("/businessData")
  @Operation(summary = "Business Data")
  public Result businessData() {
    log.info("Business Data");
    LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
    LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);
    BusinessDataVO businessDataVO = workspaceService.getBusinessData(begin, end);
    return Result.success(businessDataVO);
  }

  @GetMapping("/overviewOrders")
  @Operation(summary = "Order Overview")
  public Result orderOverView() {
    log.info("Order Overview");
    return Result.success(workspaceService.getOrderOverView());
  }

  @GetMapping("/overviewDishes")
  @Operation(summary = "Dish Overview")
  public Result dishOverView() {
    log.info("Dish Overview");
    return Result.success(workspaceService.getDishOverView());
  }

  @GetMapping("/overviewSetmeals")
  @Operation(summary = "Setmeal Overview")
  public Result setmealOverView() {
    return Result.success(workspaceService.getSetmealOverView());
  }
}
