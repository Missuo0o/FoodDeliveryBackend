package com.missuo.server.mapper;

import com.missuo.pojo.entity.OrderDetail;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderDetailMapper {
  void insertBatch(List<OrderDetail> orderDetails);

  @Select("select * from order_detail where order_id = #{id}")
  List<OrderDetail> getByOrderId(Long id);
}
