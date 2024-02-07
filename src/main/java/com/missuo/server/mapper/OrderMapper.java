package com.missuo.server.mapper;

import com.missuo.pojo.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
  void insert(Orders orders);
}
