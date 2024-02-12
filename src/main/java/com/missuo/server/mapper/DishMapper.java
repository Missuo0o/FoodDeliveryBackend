package com.missuo.server.mapper;

import com.github.pagehelper.Page;
import com.missuo.common.enumeration.OperationType;
import com.missuo.pojo.dto.DishPageQueryDTO;
import com.missuo.pojo.entity.Dish;
import com.missuo.pojo.vo.DishVO;
import com.missuo.server.annotation.AutoFill;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

  @Select("select count(id) from dish where category_id = #{categoryId}")
  Integer countByCategoryId(Long categoryId);

  @AutoFill(value = OperationType.INSERT)
  void insert(Dish dish);

  Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

  List<Dish> getByIds(List<Long> ids);

  void deleteBatch(List<Long> ids);

  @Select("select * from dish where id = #{id}")
  Dish getById(Long id);

  @AutoFill(value = OperationType.UPDATE)
  void update(Dish dish);

  List<Dish> list(Dish dish);

  @Select(
      "select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
  List<Dish> getBySetmealId(Long id);

  Integer countByMap(Map<String, Object> map);
}
