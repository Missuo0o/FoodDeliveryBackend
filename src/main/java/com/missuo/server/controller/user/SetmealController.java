package com.missuo.server.controller.user;

import com.missuo.common.constant.StatusConstant;
import com.missuo.common.result.Result;
import com.missuo.pojo.entity.Setmeal;
import com.missuo.pojo.vo.DishItemVO;
import com.missuo.server.service.SetmealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@RequiredArgsConstructor
@Tag(name = "Setmeal Management")
public class SetmealController {
  private final SetmealService setmealService;

  @GetMapping("/list")
  @Operation(summary = "Get Setmeal List")
  @Cacheable(value = "setmealCache", key = "#categoryId")
  public Result list(Long categoryId) {
    Setmeal setmeal = new Setmeal();
    setmeal.setCategoryId(categoryId);
    setmeal.setStatus(StatusConstant.ENABLE);

    List<Setmeal> list = setmealService.list(setmeal);
    return Result.success(list);
  }

  @GetMapping("/dish/{id}")
  @Operation(summary = "Get Dish List")
  public Result dishList(@PathVariable Long id) {
    List<DishItemVO> list = setmealService.getDishItemById(id);
    return Result.success(list);
  }
}
