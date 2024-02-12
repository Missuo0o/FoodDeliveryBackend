package com.missuo.server.service;

import com.missuo.pojo.vo.BusinessDataVO;
import com.missuo.pojo.vo.DishOverViewVO;
import com.missuo.pojo.vo.OrderOverViewVO;
import com.missuo.pojo.vo.SetmealOverViewVO;
import java.time.LocalDateTime;

public interface WorkspaceService {

  BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

  OrderOverViewVO getOrderOverView();

  DishOverViewVO getDishOverView();

  SetmealOverViewVO getSetmealOverView();
}
