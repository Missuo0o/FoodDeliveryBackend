package com.missuo.server.mapper;

import com.missuo.pojo.entity.OrderDetail;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper {
  void insertBatch(List<OrderDetail> orderDetails);
}
