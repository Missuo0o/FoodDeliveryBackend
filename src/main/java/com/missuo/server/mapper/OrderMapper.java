package com.missuo.server.mapper;

import com.github.pagehelper.Page;
import com.missuo.pojo.dto.OrdersPageQueryDTO;
import com.missuo.pojo.entity.Orders;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {
  void insert(Orders orders);

  @Select("select * from orders where number = #{orderNumber}")
  Orders getByNumber(String orderNumber);

  void update(Orders orders);

  Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

  @Select("select * from orders where id = #{id}")
  Orders getById(Long id);

  @Select("select count(id) from orders where status = #{toBeConfirmed}")
  Integer countStatus(Integer toBeConfirmed);

  @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
  List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);
}
