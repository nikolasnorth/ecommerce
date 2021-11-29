package com.nikolasnorth.authservice.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SnsService {

  private final AmazonSNS amazonSns;

  @Value("${sns.topic.arn}")
  private String healthStatusTopicArn;

  @Autowired
  public SnsService(AmazonSNS a) {
    this.amazonSns = a;
  }

  public void publishToTopic(String topicArn, String message) {
    try {
      PublishResult result = amazonSns.publish(topicArn, message); // Publish message to specified topic
      System.out.println(result.getMessageId() + " Message sent. Status is " + result.getSdkHttpMetadata().getHttpStatusCode());
    } catch (AmazonSNSException e) {
      System.err.println(e.getErrorMessage());
    }
  }

  @Scheduled(fixedDelay = 15_000)  // 15 seconds
  public void publishHealthStatus() {
//    PublishResult res = amazonSns.publish(healthStatusTopicArn, "auth-service");
//    System.out.printf("Message ID: %s%n", res.getMessageId());
  }
}
