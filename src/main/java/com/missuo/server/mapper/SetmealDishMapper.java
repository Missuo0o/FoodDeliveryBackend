package com.missuo.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealDishMapper {

  List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
