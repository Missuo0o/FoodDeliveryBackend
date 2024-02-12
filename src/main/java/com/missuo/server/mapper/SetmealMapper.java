package com.missuo.server.mapper;

import com.missuo.common.enumeration.OperationType;
import com.missuo.pojo.entity.Setmeal;
import com.missuo.pojo.vo.DishItemVO;
import com.missuo.server.annotation.AutoFill;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {

  @Select("select count(id) from setmeal where category_id = #{categoryId}")
  Integer countByCategoryId(Long id);

  @AutoFill(value = OperationType.INSERT)
  void insert(Setmeal setmeal);

  void deleteBatch(List<Long> ids);

  @Select("select * from setmeal where id = #{id}")
  Setmeal getById(Long id);

  @AutoFill(value = OperationType.UPDATE)
  void update(Setmeal setmeal);

  List<Setmeal> list(Setmeal setmeal);

  @Select(
      "select sd.name, sd.copies, d.image, d.description "
          + "from setmeal_dish sd left join dish d on sd.dish_id = d.id "
          + "where sd.setmeal_id = #{setmealId}")
  List<DishItemVO> getDishItemBySetmealId(Long setmealId);

  Integer countByMap(Map<String, Object> map);
}
