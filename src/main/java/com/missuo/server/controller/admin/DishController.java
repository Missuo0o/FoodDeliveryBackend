package com.missuo.server.controller.admin;

import com.missuo.common.result.Result;
import com.missuo.pojo.dto.DishDTO;
import com.missuo.server.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
