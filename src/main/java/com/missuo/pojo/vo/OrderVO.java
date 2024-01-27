package com.missuo.pojo.vo;

import com.missuo.pojo.entity.OrderDetail;
import com.missuo.pojo.entity.Orders;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO extends Orders implements Serializable {

  private String orderDishes;

  private List<OrderDetail> orderDetailList;
}
