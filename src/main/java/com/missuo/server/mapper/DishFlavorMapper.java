package com.missuo.server.mapper;

import com.missuo.pojo.entity.DishFlavor;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper {
  void insertBatch(List<DishFlavor> flavors);
}
