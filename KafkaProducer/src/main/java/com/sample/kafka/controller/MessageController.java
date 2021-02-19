package com.sample.kafka.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.kafka.model.Message;
import com.sample.kafka.service.MessageProducerService;
import com.sample.kafka.utils.Response;


@RestController
@RequestMapping("message")
public class MessageController {
	private static final Logger logger = LogManager.getLogger(MessageController.class);	
	@Autowired
	private MessageProducerService messageProducerService;
	
	
	@PostMapping("publish")
	public Response publishMessage(@RequestBody Message message) {
		logger.info("Published Message: "+ message );
		return messageProducerService.produceMessage(message);
	}
	
	
}
