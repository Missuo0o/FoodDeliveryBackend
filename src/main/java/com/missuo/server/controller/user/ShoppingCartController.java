package com.missuo.server.controller.user;

import com.missuo.common.result.Result;
import com.missuo.pojo.dto.ShoppingCartDTO;
import com.missuo.pojo.entity.ShoppingCart;
import com.missuo.server.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/shoppingCart")
@Tag(name = "Shopping Cart Management")
@Slf4j
public class ShoppingCartController {
  @Autowired private ShoppingCartService shoppingCartService;

  @PostMapping("/add")
  @Operation(summary = "Add to Shopping Cart")
  public Result add(@Validated @RequestBody ShoppingCartDTO shoppingCartDTO) {
    log.info("Add to Shopping Cart：{}", shoppingCartDTO);
    shoppingCartService.addShoppingCart(shoppingCartDTO);
    return Result.success();
  }

  @GetMapping("/list")
  @Operation(summary = "Shopping Cart List")
  public Result list() {
    log.info("Shopping Cart List");
    List<ShoppingCart> list = shoppingCartService.showShoppingCart();
    return Result.success(list);
  }

  @PostMapping("/sub")
  @Operation(summary = "Delete from Shopping Cart")
  public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
    log.info("Delete from Shopping Cart：{}", shoppingCartDTO);
    shoppingCartService.subShoppingCart(shoppingCartDTO);
    return Result.success();
  }

  @DeleteMapping("/clean")
  @Operation(summary = "Empty Shopping Cart")
  public Result clean() {
    log.info("Empty Shopping Cart");
    shoppingCartService.cleanShoppingCart();
    return Result.success();
  }
}
