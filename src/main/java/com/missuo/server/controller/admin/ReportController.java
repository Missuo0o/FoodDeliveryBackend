package com.missuo.server.controller.admin;

import com.missuo.common.result.Result;
import com.missuo.server.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/report")
@Slf4j
@Tag(name = "Report Management")
public class ReportController {
  @Autowired private ReportService reportService;

  @GetMapping("/turnoverStatistics")
  @Operation(summary = "Turnover Statistics")
  public Result turnoverStatistics(
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
    log.info("Turnover Statistic: begin={}, end={}", begin, end);
    return Result.success(reportService.getTurnoversStatistics(begin, end));
  }

  @GetMapping("/userStatistics")
  @Operation(summary = "User Statistics")
  public Result userStatistics(
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
    log.info("User Statistic: begin={}, end={}", begin, end);
    return Result.success(reportService.getUserStatistics(begin, end));
  }
}
