package com.missuo.server.aop;

import com.missuo.common.constant.AutoFillConstant;
import com.missuo.common.context.BaseContext;
import com.missuo.common.enumeration.OperationType;
import com.missuo.server.annotation.AutoFill;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
  // Proxy objects do not inherit annotations, mybatis use proxy objects, so we can't annotate in
  // the interface
  @Pointcut("@annotation(com.missuo.server.annotation.AutoFill)")
  public void autoFillPointCut() {}

  @Before("autoFillPointCut()")
  public void autoFill(JoinPoint joinPoint)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    log.info("autoFill");
    // Get annotation operation type
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
    OperationType operationType = annotation.value();

    Object[] args = joinPoint.getArgs();
    if (args == null || args.length == 0) {
      return;
    }

    Object entity = args[0];

    LocalDateTime now = LocalDateTime.now();
    Long currentId = BaseContext.getCurrentId();

    if (OperationType.INSERT.equals(operationType)) { // Insert
      Method setCreateTime =
          entity
              .getClass()
              .getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
      Method setCreateUser =
          entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
      Method setUpdateTime =
          entity
              .getClass()
              .getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
      Method setUpdateUser =
          entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

      setCreateTime.invoke(entity, now);
      setCreateUser.invoke(entity, currentId);
      setUpdateTime.invoke(entity, now);
      setUpdateUser.invoke(entity, currentId);

    } else if (OperationType.UPDATE.equals(operationType)) { // Update
      Method setUpdateTime =
          entity
              .getClass()
              .getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
      Method setUpdateUser =
          entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

      setUpdateTime.invoke(entity, now);
      setUpdateUser.invoke(entity, currentId);
    }
  }
}
