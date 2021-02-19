package com.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
	
	@KafkaListener(topics="mytopic", groupId="consumer-group")
	public void consume(String message) {
		System.out.println("Message Consumed: "+ message);
	}

}