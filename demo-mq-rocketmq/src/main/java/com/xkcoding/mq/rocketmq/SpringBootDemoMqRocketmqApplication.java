package com.xkcoding.mq.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootDemoMqRocketmqApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDemoMqRocketmqApplication.class, args);
  }

}

