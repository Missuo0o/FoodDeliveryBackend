package com.missuo.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.springframework.stereotype.Component;

@Component
public class TrimStrUtil {
  public Object trimStrings(Object arg) throws IllegalAccessException {
    if (arg instanceof String) {
      return ((String) arg).trim();
    } else if (arg != null) {
      Field[] fields = arg.getClass().getDeclaredFields();
      for (Field field : fields) {

        int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
          continue;
        }

        field.setAccessible(true);
        Object fieldValue = field.get(arg);
        if (fieldValue instanceof String) {
          field.set(arg, ((String) fieldValue).trim());
        } else if (fieldValue != null) {
          field.set(arg, fieldValue);
        }
      }
    }
    return arg;
  }
}
