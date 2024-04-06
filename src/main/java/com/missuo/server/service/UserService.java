package com.missuo.server.service;

import com.missuo.pojo.dto.UserLoginDTO;
import com.missuo.pojo.entity.User;
import java.io.IOException;

public interface UserService {
  User vxLogin(UserLoginDTO userLoginDTO) throws IOException;
}
