package com.missuo.server.service;

import com.missuo.pojo.vo.OrderReportVO;
import com.missuo.pojo.vo.SalesTop10ReportVO;
import com.missuo.pojo.vo.TurnoverReportVO;
import com.missuo.pojo.vo.UserReportVO;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public interface ReportService {

  TurnoverReportVO getTurnoversStatistics(LocalDate begin, LocalDate end);

  UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

  OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);

  SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);

  void export(HttpServletResponse response) throws IOException;
}
