package com.missuo.server.service.impl;

import com.missuo.pojo.dto.DishDTO;
import com.missuo.pojo.entity.Dish;
import com.missuo.pojo.entity.DishFlavor;
import com.missuo.server.mapper.DishFlavorMapper;
import com.missuo.server.mapper.DishMapper;
import com.missuo.server.service.DishService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DishServiceImpl implements DishService {
  @Autowired private DishMapper dishMapper;
  @Autowired private DishFlavorMapper dishFlavorMapper;

  @Override
  @Transactional
  public void saveWithFlavor(DishDTO dishDTO) {
    Dish dish = new Dish();
    BeanUtils.copyProperties(dishDTO, dish);
    dishMapper.insert(dish);

    List<DishFlavor> flavors = dishDTO.getFlavors();
    if (flavors != null && !flavors.isEmpty()) {
      flavors.forEach(
          flavor -> {
            flavor.setDishId(dish.getId());
          });

      dishFlavorMapper.insertBatch(flavors);
    }
  }
}
