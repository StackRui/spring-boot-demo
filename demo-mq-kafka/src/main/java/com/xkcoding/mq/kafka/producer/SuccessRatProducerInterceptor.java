package com.xkcoding.mq.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class SuccessRatProducerInterceptor implements ProducerInterceptor<String, String> {
  private static final LongAdder successCount = new LongAdder();
  private static final LongAdder totalCount = new LongAdder();

  @Override
  public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
    log.info("CustomProducerInterceptor start.");
    return producerRecord;
  }

  @Override
  public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
    totalCount.increment();
    if (e == null) {
      successCount.increment();
    }
  }

  @Override
  public void close() {
    double successRat = successCount.doubleValue() / totalCount.doubleValue();
    log.info("发送成功率是 {}/{}={}", successCount.longValue(), totalCount.longValue(), successRat);
  }

  @Override
  public void configure(Map<String, ?> map) {

  }
}
