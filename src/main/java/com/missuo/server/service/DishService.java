package com.missuo.server.service;

import com.missuo.common.result.PageResult;
import com.missuo.pojo.dto.DishDTO;
import com.missuo.pojo.dto.DishPageQueryDTO;
import com.missuo.pojo.vo.DishVO;
import java.util.List;

public interface DishService {

  void saveWithFlavor(DishDTO dishDTO);

  PageResult<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

  void deleteBatch(List<Long> ids);
}
