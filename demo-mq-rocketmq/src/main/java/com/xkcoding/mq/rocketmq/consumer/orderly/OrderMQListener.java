package com.xkcoding.mq.rocketmq.consumer.orderly;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "order", consumerGroup = "order-group", consumeThreadMax = 1, selectorType = SelectorType.SQL92, selectorExpression = "items>30")
public class OrderMQListener implements RocketMQListener<MessageExt> {

  @Override
  public void onMessage(MessageExt message) {
    // 处理收到的消息
    System.out.println("Received message: " + new String(message.getBody()) + ", key = " + message.getKeys() + ", tag = " + message.getTags() + ", properties = " + JSON.toJSONString(message.getProperties()));
  }
}
