package com.missuo.server.controller.admin;

import com.missuo.common.result.PageResult;
import com.missuo.common.result.Result;
import com.missuo.pojo.dto.CategoryDTO;
import com.missuo.pojo.dto.CategoryPageQueryDTO;
import com.missuo.pojo.entity.Category;
import com.missuo.server.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@Tag(name = "Category Management")
@Slf4j
public class CategoryController {

  @Autowired private CategoryService categoryService;

  @PostMapping
  @Operation(summary = "Add Category")
  public Result save(@RequestBody CategoryDTO categoryDTO) {
    log.info("Add Category：{}", categoryDTO);
    categoryService.save(categoryDTO);
    return Result.success();
  }

  @GetMapping("/page")
  @Operation(summary = "Page Query Category")
  public Result page(CategoryPageQueryDTO categoryPageQueryDTO) {
    log.info("Page Query Category：{}", categoryPageQueryDTO);
    PageResult<Category> pageResult = categoryService.pageQuery(categoryPageQueryDTO);
    return Result.success(pageResult);
  }

  @DeleteMapping
  @Operation(summary = "Delete Category")
  public Result deleteById(Long id) {
    log.info("Delete Category：{}", id);
    categoryService.deleteById(id);
    return Result.success();
  }

  @PutMapping
  @Operation(summary = "Update Category")
  public Result update(@RequestBody CategoryDTO categoryDTO) {
    log.info("Update Category：{}", categoryDTO);
    categoryService.update(categoryDTO);
    return Result.success();
  }

  @PutMapping("/status/{status}")
  @Operation(summary = "Start or Stop Category")
  public Result startOrStop(@PathVariable("status") Integer status, Long id) {
    log.info("Start or Stop Category：{},{}", status, id);
    categoryService.startOrStop(status, id);
    return Result.success();
  }

  @GetMapping("/list")
  @Operation(summary = "List Category")
  public Result list(Integer type) {
    List<Category> list = categoryService.list(type);
    return Result.success(list);
  }
}
