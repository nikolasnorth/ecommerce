package com.nikolasnorth.accountservice.sns;

import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SnsService {

  private final AmazonSNS amazonSns;

  private final String snsTopicArn;

  private final String baseUrl;

  private final Integer port;

  @Autowired
  public SnsService(
    AmazonSNS amazonSns,
    @Value("${sns.topic.arn}") String snsTopicArn,
    @Value("${baseurl}") String baseUrl,
    @Value("${server.port}") Integer port
  ) {
    this.amazonSns = amazonSns;
    this.snsTopicArn = snsTopicArn;
    this.baseUrl = baseUrl;
    this.port = port;
  }

  @Scheduled(fixedDelay = 86_400_400)  // 1 day in ms  TODO: change later
  public void publish() {
    final String url = String.format("%s:%d", baseUrl, port);
    final String msg = String.format("%d|%s|%s|%s|%s", 2, "Account_Service", url, "Active", "Null");
    amazonSns.publish(snsTopicArn, msg);
  }
}
