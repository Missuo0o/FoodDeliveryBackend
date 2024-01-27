package com.missuo.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {

  @Select("select count(id) from setmeal where category_id = #{categoryId}")
  Integer countByCategoryId(Long id);
}
