package com.missuo.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.missuo.common.constant.MessageConstant;
import com.missuo.common.constant.StatusConstant;
import com.missuo.common.exception.DeletionNotAllowedException;
import com.missuo.common.result.PageResult;
import com.missuo.pojo.dto.SetmealDTO;
import com.missuo.pojo.dto.SetmealPageQueryDTO;
import com.missuo.pojo.entity.Dish;
import com.missuo.pojo.entity.Setmeal;
import com.missuo.pojo.entity.SetmealDish;
import com.missuo.pojo.vo.SetmealVO;
import com.missuo.server.mapper.DishMapper;
import com.missuo.server.mapper.SetmealDishMapper;
import com.missuo.server.mapper.SetmealMapper;
import com.missuo.server.service.SetmealService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetmealServiceimpl implements SetmealService {
  @Autowired private SetmealMapper setmealMapper;
  @Autowired private SetmealDishMapper setmealDishMapper;
  @Autowired private DishMapper dishMapper;

  @Override
  @Transactional
  public void saveWithDish(SetmealDTO setmealDTO) {
    Setmeal setmeal = new Setmeal();
    BeanUtils.copyProperties(setmealDTO, setmeal);
    setmealMapper.insert(setmeal);

    Long id = setmeal.getId();

    setmealDTO.getSetmealDishes().forEach(setmealDish -> setmealDish.setSetmealId(id));

    setmealDishMapper.insertBatch(setmealDTO.getSetmealDishes());
  }

  @Override
  public PageResult<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
    PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
    Page<SetmealVO> page = setmealDishMapper.pageQuery(setmealPageQueryDTO);

    if (setmealPageQueryDTO.getPage() > page.getPages()) {
      PageHelper.startPage(page.getPages(), setmealPageQueryDTO.getPageSize());
      page = setmealDishMapper.pageQuery(setmealPageQueryDTO);
    }
    return new PageResult<>(page.getTotal(), page.getResult());
  }

  @Override
  @Transactional
  public void deleteBatch(List<Long> ids) {
    List<Setmeal> byIds = setmealDishMapper.getByIds(ids);
    // Presence of dishes on sale
    if (byIds == null || byIds.isEmpty()) {
      return;
    }

    if (byIds.stream()
        .anyMatch(setmeal -> Objects.equals(setmeal.getStatus(), StatusConstant.ENABLE))) {
      throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
    }

    // Delete Combo
    setmealMapper.deleteBatch(ids);
    // Delete Combo Dish
    setmealDishMapper.deleteBatchBySetmealIds(ids);
  }

  @Override
  public SetmealVO getByIdWithDish(Long id) {
    Setmeal setmeal = setmealMapper.getById(id);
    List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);

    SetmealVO setmealVO = new SetmealVO();
    BeanUtils.copyProperties(setmeal, setmealVO);

    setmealVO.setSetmealDishes(setmealDishes);
    return setmealVO;
  }

  @Override
  public void update(SetmealDTO setmealDTO) {
    Setmeal setmeal = new Setmeal();
    BeanUtils.copyProperties(setmealDTO, setmeal);
    setmealMapper.update(setmeal);

    setmealDishMapper.deleteBySetmealId(setmeal.getId());
    List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
    if (setmealDishes != null && !setmealDishes.isEmpty()) {
      setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
      setmealDishMapper.insertBatch(setmealDTO.getSetmealDishes());
    }
  }

  @Override
  public void startOrStop(Integer status, Long id) {
    //    Determine if there are any discontinued dish in the combo
    if (Objects.equals(status, StatusConstant.ENABLE)) {
      List<Dish> bySetmealId = dishMapper.getBySetmealId(id);
      if (bySetmealId == null || bySetmealId.isEmpty()) {
        return;
      }
      if (bySetmealId.stream()
          .anyMatch(dish -> Objects.equals(dish.getStatus(), StatusConstant.DISABLE))) {
        throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
      }
    }
    Setmeal setmeal = Setmeal.builder().id(id).status(status).build();
    setmealMapper.update(setmeal);
  }
}
