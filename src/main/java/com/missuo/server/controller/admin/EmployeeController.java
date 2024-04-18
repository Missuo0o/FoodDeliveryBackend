package com.missuo.server.controller.admin;

import com.missuo.common.constant.JwtClaimsConstant;
import com.missuo.common.constant.MessageConstant;
import com.missuo.common.context.BaseContext;
import com.missuo.common.exception.IllegalException;
import com.missuo.common.properties.JwtProperties;
import com.missuo.common.result.PageResult;
import com.missuo.common.result.Result;
import com.missuo.common.utils.JwtUtil;
import com.missuo.pojo.dto.EmployeeDTO;
import com.missuo.pojo.dto.EmployeeLoginDTO;
import com.missuo.pojo.dto.EmployeePageQueryDTO;
import com.missuo.pojo.dto.PasswordEditDTO;
import com.missuo.pojo.entity.Employee;
import com.missuo.pojo.vo.EmployeeLoginVO;
import com.missuo.server.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/employee")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Employee Management")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final JwtProperties jwtProperties;
  private final JwtUtil jwtUtil;
  private final RedisTemplate<Object, Object> redisTemplate;

  @PostMapping("/login")
  @Operation(summary = "Employee Login")
  public Result login(@Validated @RequestBody EmployeeLoginDTO employeeLoginDTO) {
    log.info("Employee Login：{}", employeeLoginDTO);

    Employee employee = employeeService.login(employeeLoginDTO);

    // Generate jwt token after successful login
    Map<String, Object> claims = new HashMap<>();
    claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
    String token =
        jwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);

    EmployeeLoginVO employeeLoginVO =
        EmployeeLoginVO.builder()
            .id(employee.getId())
            .userName(employee.getUsername())
            .name(employee.getName())
            .token(token)
            .build();

    return Result.success(employeeLoginVO);
  }

  @PostMapping("/logout")
  @Operation(summary = "Employee Logout")
  public Result logout() {
    redisTemplate.delete("Employee_id" + BaseContext.getCurrentId());
    BaseContext.removeCurrentId();
    return Result.success();
  }

  @PostMapping
  @Operation(summary = "Add Employee")
  public Result save(@Validated @RequestBody EmployeeDTO employeeDTO) {
    log.info("Add Employee: {}", employeeDTO);
    employeeService.save(employeeDTO);
    return Result.success();
  }

  @GetMapping("/page")
  @Operation(summary = "Employee Page Query")
  //    https://github.com/swagger-api/swagger-core/issues/4177
  public Result page(EmployeePageQueryDTO employeePageQueryDTO) {
    log.info("Employee Page Query: {}", employeePageQueryDTO);
    PageResult<Employee> pageResult = employeeService.pageQuery(employeePageQueryDTO);
    return Result.success(pageResult);
  }

  @PutMapping("/status/{status}")
  @Operation(summary = "Employee Start or Stop")
  public Result startOrStop(@PathVariable Integer status, @RequestParam Long id) {
    if (status != 1 && status != 0) {
      throw new IllegalException(MessageConstant.ILLEGAL_OPERATION);
    }
    log.info("Employee Start or Stop: {},{}", status, id);
    employeeService.startOrStop(status, id);
    return Result.success();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Employee Get By Id")
  public Result getById(@PathVariable Long id) {
    log.info("Employee Get By Id: {}", id);
    Employee employee = employeeService.getById(id);
    return Result.success(employee);
  }

  @PutMapping
  @Operation(summary = "Employee Update")
  public Result update(@Validated(EmployeeDTO.Update.class) @RequestBody EmployeeDTO employeeDTO) {
    log.info("Employee Update: {}", employeeDTO);
    employeeService.update(employeeDTO);
    return Result.success();
  }

  @PutMapping("/editPassword")
  @Operation(summary = "Employee Update Password")
  public Result editPassword(@Validated @RequestBody PasswordEditDTO passwordEditDTO) {
    log.info("Employee Update Password: {}", passwordEditDTO);
    employeeService.updatePassword(passwordEditDTO);
    return Result.success();
  }
}
