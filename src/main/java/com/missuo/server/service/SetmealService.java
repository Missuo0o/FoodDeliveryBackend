package com.missuo.server.service;

import com.missuo.common.result.PageResult;
import com.missuo.pojo.dto.SetmealDTO;
import com.missuo.pojo.dto.SetmealPageQueryDTO;
import com.missuo.pojo.entity.Setmeal;
import com.missuo.pojo.vo.DishItemVO;
import com.missuo.pojo.vo.SetmealVO;
import java.util.List;

public interface SetmealService {
  void saveWithDish(SetmealDTO setmealDTO);

  PageResult<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

  void deleteBatch(List<Long> ids);

  SetmealVO getByIdWithDish(Long id);

  void update(SetmealDTO setmealDTO);

  void startOrStop(Integer status, Long id);

  List<Setmeal> list(Setmeal setmeal);

  List<DishItemVO> getDishItemById(Long id);
}
