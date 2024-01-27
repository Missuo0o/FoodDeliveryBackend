package com.missuo.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

  @Select("select count(id) from dish where category_id = #{categoryId}")
  Integer countByCategoryId(Long categoryId);
}
