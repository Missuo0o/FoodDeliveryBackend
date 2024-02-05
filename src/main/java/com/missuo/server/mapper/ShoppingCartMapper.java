package com.missuo.server.mapper;

import com.missuo.pojo.entity.ShoppingCart;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ShoppingCartMapper {
  List<ShoppingCart> list(ShoppingCart shoppingCart);

  @Update("update shopping_cart set number = #{number} where id = #{id}")
  void updateNumberById(ShoppingCart shoppingCart);

  @Insert(
      "insert into shopping_cart (user_id, dish_id, setmeal_id, dish_flavor, name, image, amount, number, create_time) values (#{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{name}, #{image}, #{amount}, #{number}, #{createTime})")
  void insert(ShoppingCart shoppingCart);

  
  @Delete("delete from shopping_cart where id = #{id}")
  void deleteById(Long id);

  @Delete("delete from shopping_cart where user_id = #{currentId}")
  void deleteByUserId(Long currentId);
}
