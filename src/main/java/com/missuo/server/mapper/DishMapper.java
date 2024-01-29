package com.missuo.server.mapper;

import com.github.pagehelper.Page;
import com.missuo.common.enumeration.OperationType;
import com.missuo.pojo.dto.DishPageQueryDTO;
import com.missuo.pojo.entity.Dish;
import com.missuo.pojo.vo.DishVO;
import com.missuo.server.annotation.AutoFill;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

  @Select("select count(id) from dish where category_id = #{categoryId}")
  Integer countByCategoryId(Long categoryId);

  @AutoFill(value = OperationType.INSERT)
  void insert(Dish dish);

  Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

  List<Dish> getByIds(List<Long> id);

  void deleteBatch(List<Long> ids);
}
