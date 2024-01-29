package com.missuo.server.controller.admin;

import com.missuo.common.result.PageResult;
import com.missuo.common.result.Result;
import com.missuo.pojo.dto.DishDTO;
import com.missuo.pojo.dto.DishPageQueryDTO;
import com.missuo.pojo.vo.DishVO;
import com.missuo.server.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dish")
@Tag(name = "Dish Management")
@Slf4j
public class DishController {
  @Autowired private DishService dishService;

  @PostMapping
  @Operation(summary = "Add Dish")
  public Result save(@RequestBody DishDTO dishDTO) {
    log.info("Add Dish：{}", dishDTO);
    dishService.saveWithFlavor(dishDTO);
    return Result.success();
  }

  @GetMapping("/page")
  @Operation(summary = "Get Dish")
  public Result page(DishPageQueryDTO dishPageQueryDTO) {
    log.info("Get Dish：{}", dishPageQueryDTO);
    PageResult<DishVO> pageResult = dishService.pageQuery(dishPageQueryDTO);
    return Result.success(pageResult);
  }

  @DeleteMapping()
  @Operation(summary = "Delete Dish")
  public Result delete(@RequestParam("ids") List<Long> ids) {
    log.info("Delete Dish：{}", ids);
    dishService.deleteBatch(ids);
    return Result.success();
  }
}
