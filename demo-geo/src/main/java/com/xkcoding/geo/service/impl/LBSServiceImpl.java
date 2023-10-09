package com.xkcoding.geo.service.impl;

import com.xkcoding.geo.service.ILBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LBSServiceImpl implements ILBSService {
  private static final String GEO_KEY = "user_geo";

  @Autowired
  private RedisTemplate redisTemplate;

  /**
   * 上传位置
   */
  public void save(Point point, String userId) {
    redisTemplate.opsForGeo().add(GEO_KEY, new RedisGeoCommands.GeoLocation<>(userId, point));
  }

  /**
   * 附近的人
   *
   * @param point 用户自己的位置
   */
  public GeoResults<RedisGeoCommands.GeoLocation> near(Point point) {
    // 半径 3000米
    Distance distance = new Distance(3000, RedisGeoCommands.DistanceUnit.METERS);
    Circle circle = new Circle(point, distance);
    // 附近5个人 -- 业务需求
    RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortDescending().limit(5);
    GeoResults<RedisGeoCommands.GeoLocation> geoResults = redisTemplate.opsForGeo().radius(GEO_KEY, circle, geoRadiusCommandArgs);
    return geoResults;
  }
}
