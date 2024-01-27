package com.missuo.common.context;

public class BaseContext {

  public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

  public static Long getCurrentId() {
    return threadLocal.get();
  }

  public static void setCurrentId(Long id) {
    threadLocal.set(id);
  }

  public static void removeCurrentId() {
    threadLocal.remove();
  }
}
