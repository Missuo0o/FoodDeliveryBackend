package com.missuo.server.mapper;

import com.missuo.common.enumeration.OperationType;
import com.missuo.pojo.entity.Setmeal;
import com.missuo.server.annotation.AutoFill;
import java.util.List;
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
}
