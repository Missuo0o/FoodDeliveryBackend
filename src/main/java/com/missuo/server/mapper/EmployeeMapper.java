package com.missuo.server.mapper;

import com.github.pagehelper.Page;
import com.missuo.common.enumeration.OperationType;
import com.missuo.pojo.dto.EmployeePageQueryDTO;
import com.missuo.pojo.entity.Employee;
import com.missuo.server.annotation.AutoFill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

  @Select("select * from employee where username = #{username}")
  Employee getByUsername(String username);

  @Insert(
      "insert into employee (name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) "
          + "values "
          + "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
  @AutoFill(value = OperationType.INSERT)
  void insert(Employee employee);

  Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

  @AutoFill(value = OperationType.UPDATE)
  void update(Employee employee);

  @Select("select * from employee where id = #{id}")
  Employee getById(Long id);
}
