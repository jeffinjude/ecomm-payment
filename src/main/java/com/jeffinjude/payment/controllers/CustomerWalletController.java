package com.jeffinjude.payment.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeffinjude.payment.entities.CustomerWallet;
import com.jeffinjude.payment.services.CustomerWalletService;

@RestController
@RequestMapping("api/v1/payment/wallet")
public class CustomerWalletController {
	
	@Value("${ecomm.payment.teststatus}")
	private String testProp;

	
	@GetMapping("test")
	public ResponseEntity<String> test() {	
		return ResponseEntity.ok("Payment Test status: " + testProp);
	}
	
	@Autowired
	CustomerWalletService customerWalletService;
	
	private static final Logger log = LoggerFactory.getLogger(CustomerWalletController.class);
	
	@PostMapping("create")
	public ResponseEntity<CustomerWallet> createCustomerWallet(@RequestBody CustomerWallet customerWallet) {
		ResponseEntity<CustomerWallet> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			CustomerWallet createdWallet = customerWalletService.createCustomerWalletService(customerWallet);
			log.info("Inside createCustomerWallet. Created wallet: " + createdWallet.toString());
			responseEntity = new ResponseEntity<>(createdWallet, HttpStatus.OK);
		}
		catch(Exception e) {
			log.debug("Inside createCustomerWallet. Exception: " + e.getMessage());
			CustomerWallet errWalletCreation = new CustomerWallet();
			errWalletCreation.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(errWalletCreation, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@GetMapping("details/{customerId}")
	public ResponseEntity<CustomerWallet> getWalletDetails(@PathVariable("customerId") int customerId) {
		ResponseEntity<CustomerWallet> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			Optional<CustomerWallet> fetchedWalletDetails = customerWalletService.getWalletDetailsService(customerId);
			log.info("Inside getWalletDetails. Fetched wallet details: " + fetchedWalletDetails.toString());
			if(fetchedWalletDetails.isPresent()) {
				responseEntity = new ResponseEntity<>(fetchedWalletDetails.get(), HttpStatus.OK);
			}
			else {
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
		}
		catch(Exception e) {
			log.debug("Inside getWalletDetails. Exception: " + e.getMessage());
		}
		return responseEntity;
	}
	
	@PostMapping("update/increase")
	public ResponseEntity<String> increaseWalletBalance(@RequestBody CustomerWallet customerWallet) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			customerWalletService.updateWalletBalanceService(customerWallet, "increase");
			responseEntity = new ResponseEntity<>("Wallet balance increased by " + customerWallet.getWalletBalance(), HttpStatus.OK);
		}
		catch(Exception e) {
			log.debug("Inside increaseWalletBalance. Exception: " + e.getMessage());
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@PostMapping("update/decrease")
	public ResponseEntity<String> decreaseWalletBalance(@RequestBody CustomerWallet customerWallet) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			customerWalletService.updateWalletBalanceService(customerWallet, "decrease");
			responseEntity = new ResponseEntity<>("Wallet balance decreased by " + customerWallet.getWalletBalance(), HttpStatus.OK);
		}
		catch(Exception e) {
			log.debug("Inside decreaseWalletBalance. Exception: " + e.getMessage());
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}