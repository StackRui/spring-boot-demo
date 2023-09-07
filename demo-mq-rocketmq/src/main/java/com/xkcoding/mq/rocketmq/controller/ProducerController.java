package com.xkcoding.mq.rocketmq.controller;

import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/send")
public class ProducerController {
  @Autowired
  private RocketMQTemplate rocketMQTemplate;

  @PostMapping("/syncSendOrderly")
  @ResponseBody
  public SendResult syncSendOrderly(@RequestBody String str, @RequestParam String orderId) {
    // 构建消息对象
    Map<String, Object> headers = new HashMap<>();
    headers.put("KEYS", orderId);
    headers.put("items", RandomUtils.nextInt(25,50));
    // 设置消息的 key
    return rocketMQTemplate.syncSendOrderly("order:"+orderId, new GenericMessage<>(str, headers), orderId);
  }
}
