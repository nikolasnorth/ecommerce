package com.nikolasnorth.accountservice.sns;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SnsConfig {

  @Value("${aws.accessKey}")
  private String awsAccessKey;

  @Value("${aws.secretKey}")
  private String awsSecretKey;

  @Value("${aws.region}")
  private String awsRegion;

  @Bean
  public AmazonSNS amazonSNS() {
    final var awsCredentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey));

    return AmazonSNSClientBuilder
      .standard()
      .withCredentials(awsCredentialsProvider)
      .withRegion(awsRegion)
      .build();
  }
}
