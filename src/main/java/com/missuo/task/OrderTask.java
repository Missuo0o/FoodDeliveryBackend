package com.missuo.task;

import com.missuo.pojo.entity.Orders;
import com.missuo.server.mapper.OrderMapper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderTask {
  private final OrderMapper orderMapper;

  //  @Scheduled(cron = "0 * * * * ?")
  //  public void processTimeoutOrder() {
  //    log.info("processTimeoutOrder");
  //    orderMapper
  //        .getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT,
  // LocalDateTime.now().minusMinutes(15L))
  //        .forEach(
  //            order -> {
  //              order.setStatus(Orders.CANCELLED);
  //              order.setCancelTime(LocalDateTime.now());
  //              order.setCancelReason("Payment overdue");
  //              orderMapper.update(order);
  //            });
  //  }

  @Scheduled(cron = "0 0 1 * * ?")
  public void processDeliveryOrder() {
    log.info("processDeliveryOrder");
    orderMapper
        .getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().minusHours(1L))
        .forEach(
            order -> {
              order.setStatus(Orders.COMPLETED);
              orderMapper.update(order);
            });
  }
}
