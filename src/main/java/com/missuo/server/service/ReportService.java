package com.missuo.server.service;

import com.missuo.pojo.vo.OrderReportVO;
import com.missuo.pojo.vo.SalesTop10ReportVO;
import com.missuo.pojo.vo.TurnoverReportVO;
import com.missuo.pojo.vo.UserReportVO;
import java.time.LocalDate;

public interface ReportService {

  TurnoverReportVO getTurnoversStatistics(LocalDate begin, LocalDate end);

  UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

  OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);

  SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);
}
