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

  private final String healthStatusTopicArn;

  private final String baseUrl;

  private final Integer port;

  @Autowired
  public SnsService(
    AmazonSNS amazonSns,
    @Value("${sns.topic.arn}") String healthStatusTopicArn,
    @Value("${baseUrl}") String baseUrl,
    @Value("${server.port}") Integer port
  ) {
    this.amazonSns = amazonSns;
    this.healthStatusTopicArn = healthStatusTopicArn;
    this.baseUrl = baseUrl;
    this.port = port;
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
    final String url = String.format("%s:%d", baseUrl, port);
    final String msg = String.format("%d|%s|%s|%s|%s", 3, "Auth_Service", url, "Active", "Null");
    amazonSns.publish(healthStatusTopicArn, msg);
  }
}
