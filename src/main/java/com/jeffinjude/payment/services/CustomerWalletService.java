package com.jeffinjude.payment.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.jeffinjude.payment.entities.Customer;
import com.jeffinjude.payment.entities.CustomerWallet;
import com.jeffinjude.payment.repositories.CustomerWalletRepository;

@Service
public class CustomerWalletService {
	
	private final WebClient webClient;

	
	public CustomerWalletService(WebClient webClient) {
		this.webClient = webClient;
	}
	
	private static final Logger log = LoggerFactory.getLogger(CustomerWalletService.class);
	
	@Autowired
	CustomerWalletRepository customerWalletRepository;
	
	public CustomerWallet createCustomerWalletService(CustomerWallet customerWallet) throws Exception {
		
		Optional<Customer> fetchedCustomer = Optional.ofNullable(webClient.get().uri("/ecomm-customer/api/v1/customer/details/"+customerWallet.getCustomerId()).retrieve()
			      .bodyToMono(Customer.class).block());
		log.info("Inside createCustomerWallet. fetchedCustomer: " + fetchedCustomer.toString());
		
		Optional<CustomerWallet> fetchedWalletDetails = getWalletDetailsService(customerWallet.getCustomerId());
		log.info("Inside createCustomerWallet. fetchedWalletDetails: " + fetchedWalletDetails.toString());
		
		if(fetchedCustomer.isPresent() && fetchedWalletDetails.isEmpty()) {
			return customerWalletRepository.save(customerWallet);
		}
		else {
			throw new Exception("Invalid Customer id / Wallet already created.");
		}
	}
	
	public Optional<CustomerWallet> getWalletDetailsService(int customerId) {
		Optional<CustomerWallet> walletDetails = customerWalletRepository.findByCustomerId(customerId);
		return walletDetails;
	}
	
	@Transactional
	public void updateWalletBalanceService(CustomerWallet customerWallet, String operation) throws Exception {
		Optional<Customer> fetchedCustomer = Optional.ofNullable(webClient.get().uri("/ecomm-customer/api/v1/customer/details/"+customerWallet.getCustomerId()).retrieve()
			      .bodyToMono(Customer.class).block());
		log.info("Inside updateWalletBalanceService. fetchedCustomer: " + fetchedCustomer.toString());
		
		Optional<CustomerWallet> fetchedWalletDetails = getWalletDetailsService(customerWallet.getCustomerId());
		log.info("Inside updateWalletBalanceService. fetchedWalletDetails: " + fetchedWalletDetails.toString());
		
		if(fetchedCustomer.isPresent() && fetchedWalletDetails.isPresent()) {
			double existingBalance = fetchedWalletDetails.get().getWalletBalance();
			double updatedBalance = existingBalance;
			if(operation.equalsIgnoreCase("increase")) {
				updatedBalance = existingBalance + customerWallet.getWalletBalance();
			}
			else if(operation.equalsIgnoreCase("decrease")) {
				updatedBalance = existingBalance - customerWallet.getWalletBalance();
			}
			
			if(updatedBalance >= 0) {
				customerWalletRepository.updateWalletBalance(updatedBalance, customerWallet.getCustomerId());
			}
			else {
				throw new Exception("Invalid quantity: " + updatedBalance);
			}
		}
		else {
			throw new Exception("Invalid customer id / Wallet not created.");
		}
	}
	
}
