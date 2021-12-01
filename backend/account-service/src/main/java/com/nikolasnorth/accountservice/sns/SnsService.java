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

  @Value("${baseurl}")
  private String baseUrl;

  private final AmazonSNS amazonSns;

  @Autowired
  public SnsService(AmazonSNS a) {
    this.amazonSns = a;
  }

  @Scheduled(fixedDelay = 86_400_400)  // 1 day in ms  TODO: change later
  public void publish() {
    final String msg = String.format("%d|%s|%s|%s|%s", 2, "Account_Service", baseUrl, "Active", "Null");
    PublishResult res = amazonSns.publish(snsTopicArn, msg);
//    System.out.printf("Message ID: %s%n", res.getMessageId());
  }
}
