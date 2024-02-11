package com.missuo.pojo.vo;

import java.io.Serializable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnoverReportVO implements Serializable {

  private String dateList;

  private String turnoverList;
}
