package com.nikolasnorth.accountservice.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SnsService {

  @Value("${sns.topic.arn}")
  private String snsTopicArn;

  private final AmazonSNS amazonSns;

  @Autowired
  public SnsService(AmazonSNS a) {
    this.amazonSns = a;
  }

  @Scheduled(fixedDelay = 15_000)  // 15 seconds
  public void publish() {
//    PublishResult res = amazonSns.publish(snsTopicArn, "account-service");
//    System.out.printf("Message ID: %s%n", res.getMessageId());
  }
}
