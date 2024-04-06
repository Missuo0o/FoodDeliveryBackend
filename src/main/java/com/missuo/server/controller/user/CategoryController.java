package com.missuo.server.controller.user;

import com.missuo.common.result.Result;
import com.missuo.pojo.entity.Category;
import com.missuo.server.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@RequiredArgsConstructor
@Tag(name = "Category Management")
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/list")
  @Operation(summary = "Get Category List")
  public Result list(Integer type) {
    List<Category> list = categoryService.list(type);
    return Result.success(list);
  }
}
