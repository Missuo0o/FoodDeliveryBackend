package com.missuo.server.service.impl;

import com.missuo.common.constant.MessageConstant;
import com.missuo.common.context.BaseContext;
import com.missuo.common.exception.IllegalException;
import com.missuo.pojo.dto.ShoppingCartDTO;
import com.missuo.pojo.entity.Dish;
import com.missuo.pojo.entity.Setmeal;
import com.missuo.pojo.entity.ShoppingCart;
import com.missuo.server.mapper.DishMapper;
import com.missuo.server.mapper.SetmealMapper;
import com.missuo.server.mapper.ShoppingCartMapper;
import com.missuo.server.service.ShoppingCartService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
  private final ShoppingCartMapper shoppingCartMapper;
  private final DishMapper dishMapper;
  private final SetmealMapper setmealMapper;

  @Override
  public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
    ShoppingCart shoppingCart = new ShoppingCart();
    BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
    shoppingCart.setUserId(BaseContext.getCurrentId());
    // Determine whether the product added to the shopping cart already exists
    List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
    // If the product already exists, update the quantity of the product in the shopping cart
    if (!list.isEmpty()) {
      ShoppingCart cart = list.getFirst();
      cart.setNumber(cart.getNumber() + 1);
      shoppingCartMapper.updateNumberById(cart);
    } else {
      // If the product does not exist, add the product to the shopping cart
      Long dishId = shoppingCart.getDishId();
      Long setmealId = shoppingCart.getSetmealId();
      if (dishId != null) {
        Dish dish = dishMapper.getById(dishId);
        if (dish == null) {
          throw new IllegalException(MessageConstant.ILLEGAL_OPERATION);
        }
        shoppingCart.setName(dish.getName());
        shoppingCart.setImage(dish.getImage());
        shoppingCart.setAmount(dish.getPrice());
      } else if (setmealId != null) {
        Setmeal setmeal = setmealMapper.getById(setmealId);
        if (setmeal == null) {
          throw new IllegalException(MessageConstant.ILLEGAL_OPERATION);
        }
        shoppingCart.setName(setmeal.getName());
        shoppingCart.setImage(setmeal.getImage());
        shoppingCart.setAmount(setmeal.getPrice());
      }
      shoppingCart.setNumber(1);
      shoppingCart.setCreateTime(LocalDateTime.now());
      shoppingCartMapper.insert(shoppingCart);
    }
  }

  @Override
  public List<ShoppingCart> showShoppingCart() {
    Long currentId = BaseContext.getCurrentId();
    return shoppingCartMapper.list(ShoppingCart.builder().userId(currentId).build());
  }

  @Override
  public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
    ShoppingCart shoppingCart = new ShoppingCart();
    BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
    shoppingCart.setUserId(BaseContext.getCurrentId());
    List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

    ShoppingCart cart = list.getFirst();
    if (cart.getNumber() > 1) {
      cart.setNumber(cart.getNumber() - 1);
      shoppingCartMapper.updateNumberById(cart);
    } else {
      shoppingCartMapper.deleteById(cart.getId());
    }
  }

  @Override
  public void cleanShoppingCart() {
    Long currentId = BaseContext.getCurrentId();
    shoppingCartMapper.deleteByUserId(currentId);
  }
}
