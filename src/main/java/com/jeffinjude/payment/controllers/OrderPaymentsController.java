package com.jeffinjude.payment.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeffinjude.payment.entities.OrderPayments;
import com.jeffinjude.payment.services.OrderPaymentService;

@RestController
@RequestMapping("api/v1/payment/order-payment")
public class OrderPaymentsController {
	
	@Autowired
	OrderPaymentService orderPaymentService;
	
	private static final Logger log = LoggerFactory.getLogger(OrderPaymentsController.class);
	
	@PostMapping("make-payment")
	public ResponseEntity<OrderPayments> makeOrderPayment(@RequestBody OrderPayments orderPayments) {
		ResponseEntity<OrderPayments> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			OrderPayments createdPayment = orderPaymentService.makeOrderPaymentService(orderPayments);
			log.info("Inside makeOrderPayment. Created payment: " + createdPayment.toString());
			responseEntity = new ResponseEntity<>(createdPayment, HttpStatus.OK);
		}
		catch(Exception e) {
			log.debug("Inside makeOrderPayment. Exception: " + e.getMessage());
			OrderPayments errPayment = new OrderPayments();
			errPayment.setPaymentStatus(e.getMessage());
			responseEntity = new ResponseEntity<>(errPayment, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}
