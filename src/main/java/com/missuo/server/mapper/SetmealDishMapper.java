package com.missuo.server.mapper;

import com.github.pagehelper.Page;
import com.missuo.pojo.dto.SetmealPageQueryDTO;
import com.missuo.pojo.entity.Setmeal;
import com.missuo.pojo.entity.SetmealDish;
import com.missuo.pojo.vo.SetmealVO;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealDishMapper {

  List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

  void insertBatch(List<SetmealDish> setmealDishes);

  Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

  List<Setmeal> getByIds(List<Long> ids);

  void deleteBatchBySetmealIds(List<Long> ids);

  @Select("select * from setmeal_dish where setmeal_id = #{id}")
  List<SetmealDish> getBySetmealId(Long id);

  @Delete("delete from setmeal_dish where setmeal_id = #{id}")
  void deleteBySetmealId(Long id);
}
