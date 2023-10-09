package com.xkcoding.geo.controller;

import com.xkcoding.geo.service.ILBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LBSController {

  @Autowired
  ILBSService lbsService;

  /**
   * 上传位置
   *
   * @param userId    从登录信息获取。。此处示例作为参数
   * @param latitude  纬度
   * @param longitude 经度
   */
  @RequestMapping("/geo/save")
  @ResponseBody
  public void save(String userId, String longitude, String latitude) {
    Point point = new Point(Double.valueOf(longitude), Double.valueOf(latitude));
    lbsService.save(point, userId);
  }

  @RequestMapping("/geo/near")
  @ResponseBody
  public Object near(String longitude, String latitude) {
    Point point = new Point(Double.valueOf(longitude), Double.valueOf(latitude));
    return lbsService.near(point);
  }
}
