package com.missuo.server.mapper;

import com.github.pagehelper.Page;
import com.missuo.common.enumeration.OperationType;
import com.missuo.pojo.dto.CategoryPageQueryDTO;
import com.missuo.pojo.entity.Category;
import com.missuo.server.annotation.AutoFill;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

  @Insert(
      "insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)"
          + " VALUES"
          + " (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
  @AutoFill(value = OperationType.INSERT)
  void insert(Category category);

  Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

  @Delete("delete from category where id = #{id}")
  void deleteById(Long id);

  @AutoFill(value = OperationType.UPDATE)
  void update(Category category);

  List<Category> list(Integer type);
}
