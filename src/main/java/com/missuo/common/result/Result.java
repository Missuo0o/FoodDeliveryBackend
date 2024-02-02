package com.missuo.common.result;

import com.missuo.common.constant.StatusConstant;
import java.io.Serializable;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Result implements Serializable {

  private Integer code;
  private String msg;
  private Object data;

  public static Result success() {
    Result result = new Result();
    result.code = StatusConstant.OK;
    return result;
  }

  public static Result success(Object object) {
    Result result = new Result();
    result.data = object;
    result.code = StatusConstant.OK;
    return result;
  }

  public static Result error(String msg) {
    Result result = new Result();
    result.msg = msg;
    result.code = StatusConstant.ERROR;
    return result;
  }

  public static Result unAuthorized(String msg) {
    Result result = new Result();
    result.msg = msg;
    result.code = HttpStatus.UNAUTHORIZED.value();
    return result;
  }
}
