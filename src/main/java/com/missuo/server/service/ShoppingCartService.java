package com.missuo.server.service;

import com.missuo.pojo.dto.ShoppingCartDTO;
import com.missuo.pojo.entity.ShoppingCart;
import java.util.List;

public interface ShoppingCartService {
  void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

  List<ShoppingCart> showShoppingCart();

  void subShoppingCart(ShoppingCartDTO shoppingCartDTO);

  void cleanShoppingCart();
}
