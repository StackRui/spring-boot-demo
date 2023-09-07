package com.xkcoding.mq.rocketmq.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rocketmq")
public class ProducerController {
  private final RocketMQTemplate rocketMQTemplate;

  public ProducerController(RocketMQTemplate rocketMQTemplate) {
    this.rocketMQTemplate = rocketMQTemplate;
  }

  @PostMapping("/syncSendOrderly")
  @ResponseBody
  public SendResult syncSendOrderly(@RequestBody String str, @RequestParam String orderId) {
    Map<String, Object> headers = new HashMap<>();
    headers.put("KEYS", orderId);
    headers.put("items", RandomUtils.nextInt(25, 50));
    return rocketMQTemplate.syncSendOrderly("order:" + orderId, new GenericMessage<>(str, headers), orderId);
  }

  @PostMapping("/asyncSendOrderly")
  @ResponseBody
  public void asyncSendOrderly(@RequestBody String str, @RequestParam String orderId) {
    Map<String, Object> headers = new HashMap<>();
    headers.put("KEYS", orderId);
    headers.put("items", RandomUtils.nextInt(25, 50));

    rocketMQTemplate.asyncSendOrderly("order:" + orderId, new GenericMessage<>(str, headers), orderId, new SendCallback() {
      @Override
      public void onSuccess(SendResult sendResult) {
        System.out.printf("发送消息成功!\n" + JSON.toJSONString(sendResult));
      }

      @Override
      public void onException(Throwable throwable) {
        System.out.printf("发送消息失败!\n" + throwable.getMessage());
      }
    });
  }
}
