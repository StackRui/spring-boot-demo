package com.xkcoding.ratelimit.redis.redisson;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonDemo {
  public static void main(String[] args) {
    // 配置多个Redis节点
    Config config = new Config();
    config.useClusterServers()
      .addNodeAddress("redis://node1:6379")
      .addNodeAddress("redis://node2:6379")
      .addNodeAddress("redis://node3:6379");

    // 创建Redisson客户端实例
    RedissonClient redisson = Redisson.create(config);

    // 创建RedissonRedLock
    RLock lock1 = redisson.getLock("lock1");
    RLock lock2 = redisson.getLock("lock2");
    RLock lock3 = redisson.getLock("lock3");

    // 创建一个RedissonRedLock对象，传入要协调的多个锁
    RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);

    try {
      // 尝试获取分布式锁
      redLock.lock();
      // 在这里执行需要加锁的操作
      System.out.println("成功获取分布式锁");
    } finally {
      // 释放分布式锁
      redLock.unlock();
      // 关闭Redisson客户端
      redisson.shutdown();
    }
  }
}
