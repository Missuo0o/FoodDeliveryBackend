package com.missuo.server.service.impl;

import com.missuo.pojo.dto.GoodsSalesDTO;
import com.missuo.pojo.entity.Orders;
import com.missuo.pojo.vo.*;
import com.missuo.server.mapper.OrderMapper;
import com.missuo.server.mapper.UserMapper;
import com.missuo.server.service.ReportService;
import com.missuo.server.service.WorkspaceService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
  private final OrderMapper orderMapper;
  private final UserMapper userMapper;
  private final WorkspaceService workspaceService;

  @Override
  public TurnoverReportVO getTurnoversStatistics(LocalDate begin, LocalDate end) {
    List<LocalDate> dateList = getLocalDates(begin, end);

    // Get turnover for each date in dateLis
    List<BigDecimal> turnoverList =
        dateList.stream()
            .map(
                date -> {
                  LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
                  LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
                  Map<String, Object> map = new HashMap<>();
                  map.put("beginTime", beginTime);
                  map.put("endTime", endTime);
                  map.put("status", Orders.COMPLETED);
                  BigDecimal turnover = orderMapper.sumByMap(map);
                  return turnover != null ? turnover : BigDecimal.ZERO;
                })
            .toList();

    return TurnoverReportVO.builder()
        .dateList(StringUtils.join(dateList, ","))
        .turnoverList(StringUtils.join(turnoverList, ","))
        .build();
  }

  @Override
  public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
    List<LocalDate> dateList = getLocalDates(begin, end);

    List<Integer> newUserList = new ArrayList<>();
    List<Integer> totalUserList = new ArrayList<>();

    dateList.forEach(
        date -> {
          LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
          LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
          Map<String, Object> map = new HashMap<>();
          map.put("endTime", endTime);
          int totalUser = userMapper.countByMap(map);
          map.put("beginTime", beginTime);
          Integer newUser = userMapper.countByMap(map);
          totalUserList.add(totalUser);
          newUserList.add(newUser);
        });
    return UserReportVO.builder()
        .dateList(StringUtils.join(dateList, ","))
        .newUserList(StringUtils.join(newUserList, ","))
        .totalUserList(StringUtils.join(totalUserList, ","))
        .build();
  }

  @Override
  public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
    List<LocalDate> dateList = getLocalDates(begin, end);

    List<Integer> orderCountList = new ArrayList<>();
    List<Integer> completedOrderCountList = new ArrayList<>();

    dateList.forEach(
        date -> {
          LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
          LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
          // Query the total number of orders per day
          Map<String, Object> map = new HashMap<>();
          map.put("beginTime", beginTime);
          map.put("endTime", endTime);
          Integer orderCount = userMapper.countByMap(map);
          orderCountList.add(orderCount);
          // Query the total number of completed orders per day
          map.put("status", Orders.COMPLETED);
          Integer completedOrderCount = userMapper.countByMap(map);
          completedOrderCountList.add(completedOrderCount);
        });

    Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).orElse(0);

    Integer totalCompletedOrderCount =
        completedOrderCountList.stream().reduce(Integer::sum).orElse(0);

    Double orderCompletionRate =
        totalOrderCount == 0 ? 0.0 : totalCompletedOrderCount.doubleValue() / totalOrderCount;

    return OrderReportVO.builder()
        .dateList(StringUtils.join(dateList, ","))
        .orderCountList(StringUtils.join(orderCountList, ","))
        .validOrderCountList(StringUtils.join(completedOrderCountList, ","))
        .orderCompletionRate(orderCompletionRate)
        .totalOrderCount(totalOrderCount)
        .validOrderCount(totalCompletedOrderCount)
        .build();
  }

  @Override
  public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
    LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
    LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

    Map<String, Object> map = new HashMap<>();
    map.put("beginTime", beginTime);
    map.put("endTime", endTime);
    map.put("status", Orders.COMPLETED);

    List<GoodsSalesDTO> goodsSalesList = orderMapper.getGoodsSales(map);

    List<String> name = goodsSalesList.stream().map(GoodsSalesDTO::getName).toList();
    String nameList = StringUtils.join(name, ",");
    List<Integer> number = goodsSalesList.stream().map(GoodsSalesDTO::getNumber).toList();
    String numberList = StringUtils.join(number, ",");

    return SalesTop10ReportVO.builder().nameList(nameList).numberList(numberList).build();
  }

  @Override
  public void export(HttpServletResponse response) {
    LocalDate dateBegin = LocalDate.now().minusDays(30L);
    LocalDate dateEnd = LocalDate.now().minusDays(1L);

    BusinessDataVO businessDataVO =
        workspaceService.getBusinessData(
            LocalDateTime.of(dateBegin, LocalTime.MIN), LocalDateTime.of(dateEnd, LocalTime.MAX));

    // Write data to Excel files via POIs
    InputStream resourceAsStream =
        this.getClass().getClassLoader().getResourceAsStream("template/Template.xlsx");

    // Create a new Excel file based on the template file
    try {
      XSSFWorkbook excel = new XSSFWorkbook(Objects.requireNonNull(resourceAsStream));

      XSSFSheet sheet = excel.getSheet("Sheet1");

      sheet.getRow(1).getCell(1).setCellValue("Date:" + dateBegin + " - " + dateEnd);

      XSSFRow row = sheet.getRow(3);
      row.getCell(2).setCellValue(businessDataVO.getTurnover().doubleValue());
      row.getCell(4).setCellValue(businessDataVO.getOrderCompletionRate());
      row.getCell(6).setCellValue(businessDataVO.getNewUsers());

      row = sheet.getRow(4);
      row.getCell(2).setCellValue(businessDataVO.getValidOrderCount());
      row.getCell(4).setCellValue(businessDataVO.getUnitPrice().doubleValue());

      for (int i = 0; i < 30; i++) {
        LocalDate date = dateEnd.plusDays(i);
        // Query data for a specific day
        BusinessDataVO businessData =
            workspaceService.getBusinessData(
                LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));

        row = sheet.getRow(7 + i);
        row.getCell(1).setCellValue(date.toString());
        row.getCell(2).setCellValue(businessData.getTurnover().doubleValue());
        row.getCell(3).setCellValue(businessData.getValidOrderCount());
        row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
        row.getCell(5).setCellValue(businessData.getUnitPrice().doubleValue());
        row.getCell(6).setCellValue(businessData.getNewUsers());
      }

      // The output stream is used to download the Excel file to the client's browser
      ServletOutputStream outputStream = response.getOutputStream();
      excel.write(outputStream);

      outputStream.close();
      excel.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<LocalDate> getLocalDates(LocalDate begin, LocalDate end) {
    List<LocalDate> dateList = new ArrayList<>();
    // Add all dates between begin and end to dateList
    while (!begin.isAfter(end)) {
      dateList.add(begin);
      begin = begin.plusDays(1L);
    }

    dateList.add(end);
    return dateList;
  }
}
