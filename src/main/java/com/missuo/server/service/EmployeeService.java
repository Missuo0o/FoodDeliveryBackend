package com.missuo.server.service;

import com.missuo.common.result.PageResult;
import com.missuo.pojo.dto.EmployeeDTO;
import com.missuo.pojo.dto.EmployeeLoginDTO;
import com.missuo.pojo.dto.EmployeePageQueryDTO;
import com.missuo.pojo.dto.PasswordEditDTO;
import com.missuo.pojo.entity.Employee;

public interface EmployeeService {

  Employee login(EmployeeLoginDTO employeeLoginDTO);

  void save(EmployeeDTO employeeDTO);

  PageResult<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

  void startOrStop(Integer status, Long id);

  Employee getById(Long id);

  void update(EmployeeDTO employeeDTO);

  void updatePassword(PasswordEditDTO passwordEditDTO);
}
