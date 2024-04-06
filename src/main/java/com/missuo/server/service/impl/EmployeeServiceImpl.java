package com.missuo.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.missuo.common.constant.MessageConstant;
import com.missuo.common.constant.PasswordConstant;
import com.missuo.common.constant.StatusConstant;
import com.missuo.common.context.BaseContext;
import com.missuo.common.exception.*;
import com.missuo.common.result.PageResult;
import com.missuo.pojo.dto.EmployeeDTO;
import com.missuo.pojo.dto.EmployeeLoginDTO;
import com.missuo.pojo.dto.EmployeePageQueryDTO;
import com.missuo.pojo.dto.PasswordEditDTO;
import com.missuo.pojo.entity.Employee;
import com.missuo.server.mapper.EmployeeMapper;
import com.missuo.server.service.EmployeeService;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeMapper employeeMapper;
  private final RedisTemplate<Object, Object> redisTemplate;

  public Employee login(EmployeeLoginDTO employeeLoginDTO) {
    String username = employeeLoginDTO.getUsername();
    String password = employeeLoginDTO.getPassword();

    // 1、Querying data in the database by username
    Employee employee = employeeMapper.getByUsername(username);

    // 2、Handle various abnormal situations (username does not exist, password is incorrect, account
    // is locked)
    if (employee == null) {
      // Account does not exist
      throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
    }

    // Password matching
    //        password = DigestUtils.md5DigestAsHex(password.getBytes());
    password = Md5Crypt.md5Crypt(password.getBytes(), "$1$ShunZhang");
    if (!password.equals(employee.getPassword())) {
      // Password Error
      throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
    }

    if (Objects.equals(employee.getStatus(), StatusConstant.DISABLE)) {
      // Account locked
      throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
    }
    redisTemplate
        .opsForValue()
        .set("Employee_id" + employee.getId(), employee.getId(), 2, TimeUnit.HOURS);
    return employee;
  }

  @Override
  public void save(EmployeeDTO employeeDTO) {
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeDTO, employee);

    employee.setStatus(StatusConstant.ENABLE);
    employee.setPassword(
        Md5Crypt.md5Crypt(PasswordConstant.DEFAULT_PASSWORD.getBytes(), "$1$ShunZhang"));

    employeeMapper.insert(employee);
  }

  @Override
  public PageResult<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
    PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
    Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

    // If the current page number value is greater than the total page number value, re-execute the
    // query operation and use the maximum page number value as the current page number value.
    if (employeePageQueryDTO.getPage() > page.getPages()) {
      PageHelper.startPage(page.getPages(), employeePageQueryDTO.getPageSize());
      page = employeeMapper.pageQuery(employeePageQueryDTO);
    }

    page.getResult().forEach(employee -> employee.setPassword(null));

    return new PageResult<>(page.getTotal(), page.getResult());
  }

  @Override
  public void startOrStop(Integer status, Long id) {
    Employee employee = Employee.builder().id(id).status(status).build();
    if (status == 1) {
      redisTemplate.opsForValue().set("Employee_id" + id, id, 2, TimeUnit.HOURS);
    }
    if (status == 0) {
      redisTemplate.delete("Employee_id" + id);
    }

    employeeMapper.update(employee);
  }

  @Override
  public Employee getById(Long id) {
    Employee employee = employeeMapper.getById(id);
    employee.setPassword(null);
    return employee;
  }

  @Override
  public void update(EmployeeDTO employeeDTO) {

    if (PasswordConstant.DEFAULT_USERNAME.equals(employeeDTO.getUsername())) {
      throw new IllegalException(MessageConstant.ILLEGAL_OPERATION);
    } else {
      Employee employee = new Employee();
      BeanUtils.copyProperties(employeeDTO, employee);

      employeeMapper.update(employee);
    }
  }

  @Override
  public void updatePassword(PasswordEditDTO passwordEditDTO) {
    Long currentId = BaseContext.getCurrentId();
    Employee employee = employeeMapper.getById(currentId);

    String oldPassword =
        Md5Crypt.md5Crypt(passwordEditDTO.getOldPassword().getBytes(), "$1$ShunZhang");
    if (!oldPassword.equals(employee.getPassword())) {
      throw new PasswordEditFailedException(MessageConstant.PASSWORD_EDIT_FAILED);
    }

    String newPassword =
        Md5Crypt.md5Crypt(passwordEditDTO.getNewPassword().getBytes(), "$1$ShunZhang");
    Employee newEmployee = Employee.builder().id(currentId).password(newPassword).build();

    employeeMapper.update(newEmployee);
  }
}
