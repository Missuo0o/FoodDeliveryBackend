package com.missuo.common.result;

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
    result.code = HttpStatus.OK.value();
    return result;
  }

  public static Result success(Object object) {
    Result result = new Result();
    result.data = object;
    result.code = HttpStatus.OK.value();
    return result;
  }

  public static Result error(String msg) {
    Result result = new Result();
    result.msg = msg;
    result.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    return result;
  }

  public static Result unAuthorized(String msg) {
    Result result = new Result();
    result.msg = msg;
    result.code = HttpStatus.UNAUTHORIZED.value();
    return result;
  }
}
