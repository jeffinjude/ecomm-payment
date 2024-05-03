package com.jeffinjude.payment.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.jeffinjude.payment.entities.MessagePayload;

@Service
public class Producer {
	private static final String TOPIC = "ecomm-order-status";
	
	private static final Logger log = LoggerFactory.getLogger(Producer.class);
	
	@Autowired
	private KafkaTemplate<String, MessagePayload> kafkaTemplate;
	
	public void sendMessage(MessagePayload message) {
		log.info("Inside Producer. Payload: " + message.toString());
		this.kafkaTemplate.send(TOPIC, message);
	}
}
