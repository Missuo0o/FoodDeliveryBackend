package com.missuo.server.service;

import com.missuo.common.result.PageResult;
import com.missuo.pojo.dto.CategoryDTO;
import com.missuo.pojo.dto.CategoryPageQueryDTO;
import com.missuo.pojo.entity.Category;
import java.util.List;

public interface CategoryService {

  void save(CategoryDTO categoryDTO);

  PageResult<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

  void deleteById(Long id);

  void update(CategoryDTO categoryDTO);

  void startOrStop(Integer status, Long id);

  List<Category> list(Integer type);
}
