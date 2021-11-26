package com.nikolasnorth.aws;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.PublishResult;

public class SNS {
	public static void publishToTopic(String topicArn, String message) {
    	try {
    		AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Regions.US_EAST_2).build();

    		PublishResult result = snsClient.publish(topicArn, message); // Publish message to specified topic
            System.out.println(result.getMessageId() + " Message sent. Status is " + result.getSdkHttpMetadata().getHttpStatusCode());
         } catch (AmazonSNSException e) {
            System.err.println(e.getErrorMessage());
         }
    }
}
