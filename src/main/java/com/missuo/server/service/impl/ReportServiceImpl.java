package com.missuo.server.service.impl;

import com.missuo.pojo.dto.GoodsSalesDTO;
import com.missuo.pojo.entity.Orders;
import com.missuo.pojo.vo.OrderReportVO;
import com.missuo.pojo.vo.SalesTop10ReportVO;
import com.missuo.pojo.vo.TurnoverReportVO;
import com.missuo.pojo.vo.UserReportVO;
import com.missuo.server.mapper.OrderMapper;
import com.missuo.server.mapper.UserMapper;
import com.missuo.server.service.ReportService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
  @Autowired private OrderMapper orderMapper;
  @Autowired private UserMapper userMapper;

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

  private List<LocalDate> getLocalDates(LocalDate begin, LocalDate end) {
    List<LocalDate> dateList = new ArrayList<>();
    // Add all dates between begin and end to dateList
    while (!begin.equals(end)) {
      dateList.add(begin);
      begin = begin.plusDays(1L);
    }

    dateList.add(end);
    return dateList;
  }
}
