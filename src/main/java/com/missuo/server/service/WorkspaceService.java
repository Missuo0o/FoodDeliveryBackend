package com.missuo.server.service;

import com.missuo.pojo.vo.BusinessDataVO;
import com.missuo.pojo.vo.DishOverViewVO;
import com.missuo.pojo.vo.OrderOverViewVO;
import com.missuo.pojo.vo.SetmealOverViewVO;

public interface WorkspaceService {

  BusinessDataVO getBusinessData();

  OrderOverViewVO getOrderOverView();

  DishOverViewVO getDishOverView();

  SetmealOverViewVO getSetmealOverView();
}
