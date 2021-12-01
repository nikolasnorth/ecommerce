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

  @Value("${baseurl}")
  private String baseUrl;

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

  @Scheduled(fixedDelay = 86_400_400)  // 1 day in ms TODO: change later
  public void publishHealthStatus() {
    final String msg = String.format("%d|%s|%s|%s|%s", 3, "Auth_Service", baseUrl, "Active", "Null");
    PublishResult res = amazonSns.publish(healthStatusTopicArn, msg);
//    System.out.printf("Message ID: %s%n", res.getMessageId());
  }
}
