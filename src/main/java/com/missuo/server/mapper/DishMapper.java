package com.missuo.server.mapper;

import com.missuo.common.enumeration.OperationType;
import com.missuo.pojo.entity.Dish;
import com.missuo.server.annotation.AutoFill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

  @Select("select count(id) from dish where category_id = #{categoryId}")
  Integer countByCategoryId(Long categoryId);

  @AutoFill(value = OperationType.INSERT)
  void insert(Dish dish);
}
