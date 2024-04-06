package com.missuo.pojo.message;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MultDelayMessage<T> implements Serializable {
  @Serial private static final long serialVersionUID = 1;
  private T data;
  private List<Long> delayMillis;

  public MultDelayMessage(T data, List<Long> delayMillis) {
    this.data = data;
    this.delayMillis = delayMillis;
  }

  public static <T> MultDelayMessage<T> of(T data, Long... delayMillis) {
    return new MultDelayMessage<>(data, List.of(delayMillis));
  }

  public Long removeNextDelay() {
    return delayMillis.removeFirst();
  }

  public boolean hasDelay() {
    return !delayMillis.isEmpty();
  }
}
