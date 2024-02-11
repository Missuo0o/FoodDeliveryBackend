package com.missuo.server.service.impl;

import com.missuo.pojo.entity.Orders;
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
        localDate -> {
          LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
          LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);
          Map<String, Object> map = new HashMap<>();
          map.put("endTime", endTime);
          Integer totalUser = userMapper.countByMap(map);
          if (totalUser == null) {
            totalUser = 0;
          }
          map.put("beginTime", beginTime);
          Integer newUser = userMapper.countByMap(map);
          if (newUser == null) {
            newUser = 0;
          }
          totalUserList.add(totalUser);
          newUserList.add(newUser);
        });
    return UserReportVO.builder()
        .dateList(StringUtils.join(dateList, ","))
        .newUserList(StringUtils.join(newUserList, ","))
        .totalUserList(StringUtils.join(totalUserList, ","))
        .build();
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
