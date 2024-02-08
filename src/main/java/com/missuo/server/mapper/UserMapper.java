package com.missuo.server.mapper;

import com.missuo.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  void insert(User user);

  @Select("select * from user where openid = #{openid}")
  User getByOpenid(String openid);

  @Select("select * from user where id = #{userId}")
  User getById(Long userId);
}
