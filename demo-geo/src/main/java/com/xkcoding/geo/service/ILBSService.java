package com.xkcoding.geo.service;

import org.springframework.data.geo.Point;

public interface ILBSService {

  void save(Point point, String userId);

  Object near(Point point);

}
