package com.missuo.server.mapper;

import com.github.pagehelper.Page;
import com.missuo.pojo.dto.OrdersPageQueryDTO;
import com.missuo.pojo.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {
  void insert(Orders orders);

  @Select("select * from orders where number = #{orderNumber}")
  Orders getByNumber(String orderNumber);

  void update(Orders orders);

  Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);
}
