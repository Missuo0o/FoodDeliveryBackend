package com.missuo.server.listener;

import com.missuo.common.constant.MqConstant;
import com.missuo.pojo.entity.Orders;
import com.missuo.pojo.message.MultDelayMessage;
import com.missuo.server.mapper.OrderMapper;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCheckStatusListener {
  @Autowired private OrderMapper orderMapper;
  @Autowired private RabbitTemplate rabbitTemplate;

  @RabbitListener(
      bindings =
          @QueueBinding(
              value = @Queue(value = MqConstant.DELAY_ORDER_QUEUE, durable = "true"),
              exchange =
                  @Exchange(value = MqConstant.DELAY_EXCHANGE, delayed = "true", type = "topic"),
              key = MqConstant.DELAY_ORDER_ROUTING_KEY))
  public void listenOrderDelayMessage(MultDelayMessage<Long> msg) {
    Orders orders = orderMapper.getById(msg.getData());
    if (orders == null || Objects.equals(orders.getPayStatus(), Orders.PAID)) {
      return;
    }

    if (msg.hasDelay()) {
      Long nextDelay = msg.removeNextDelay();
      rabbitTemplate.convertAndSend(
          MqConstant.DELAY_EXCHANGE,
          MqConstant.DELAY_ORDER_ROUTING_KEY,
          msg,
          message -> {
            message.getMessageProperties().setDelay(nextDelay.intValue());
            return message;
          });
    } else {
      orders.setStatus(Orders.CANCELLED);
      orders.setCancelTime(LocalDateTime.now());
      orders.setCancelReason("Payment overdue");
      orderMapper.update(orders);
    }
  }
}
