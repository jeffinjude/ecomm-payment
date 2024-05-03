package com.jeffinjude.payment.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jeffinjude.payment.entities.Product;
import com.jeffinjude.payment.entities.OrderDetailsInfo;
import com.jeffinjude.payment.entities.OrderPayments;
import com.jeffinjude.payment.entities.CustomerWallet;
import com.jeffinjude.payment.entities.InventoryDetails;
import com.jeffinjude.payment.repositories.OrderPaymentsRepository;

@Service
public class OrderPaymentService {
	
	private final WebClient webClient;

	
	public OrderPaymentService(WebClient webClient) {
		this.webClient = webClient;
	}
	
	private static final Logger log = LoggerFactory.getLogger(OrderPaymentService.class);
	
	@Autowired
	OrderPaymentsRepository orderPaymentRepository;
	
	@Autowired
	CustomerWalletService customerWalletService;
	
	public OrderPayments makeOrderPaymentService(OrderPayments orderPayments) throws Exception {
		Optional<List<OrderPayments>> fetchedOrderPayments = orderPaymentRepository.findByOrderId(orderPayments.getOrderId());
		boolean successPayment = false;
		if(fetchedOrderPayments.isPresent()) {
			successPayment = fetchedOrderPayments.get().stream().anyMatch(orderPayment -> orderPayment.getPaymentStatus().equalsIgnoreCase("SUCCESS"));
		}
		
		if(!successPayment) {
			Optional<OrderDetailsInfo> fetchedOrder = Optional.ofNullable(webClient.get().uri("/ecomm-order-processing/api/v1/order/details/"+orderPayments.getOrderId()).retrieve()
				      .bodyToMono(OrderDetailsInfo.class).block());
			log.info("Inside makeOrderPaymentService. fetchedOrder: " + fetchedOrder.toString());
			
			if(fetchedOrder.isPresent()) {
				Optional<Product> fetchedProduct = Optional.ofNullable(webClient.get().uri("/ecomm-product-catalogue/api/v1/product/details/"+fetchedOrder.get().getProduct().getProductId()).retrieve()
					      .bodyToMono(Product.class).block());
				log.info("Inside makeOrderPaymentService. fetchedProduct: " + fetchedProduct.toString());
				
				Optional<CustomerWallet> fetchedWallet = customerWalletService.getWalletDetailsService(fetchedOrder.get().getCustomer().getCustomerId());
				log.info("Inside makeOrderPaymentService. fetchedWallet: " + fetchedWallet.toString());
				
				if(fetchedProduct.isPresent() && fetchedWallet.isPresent()) {
					
					Optional<InventoryDetails> fetchedInventory = Optional.ofNullable(webClient.get().uri("/ecomm-inventory/api/v1/inventory/details/"+fetchedOrder.get().getProduct().getProductId()).retrieve()
						      .bodyToMono(InventoryDetails.class).block());
					log.info("Inside makeOrderPaymentService. fetchedInventory: " + fetchedInventory.toString());
					
					if((fetchedProduct.get().getProductPrice() <= fetchedWallet.get().getWalletBalance()) 
							&& (fetchedInventory.get().getProductQuantity() > 0)) {
						orderPayments.setPaymentStatus("SUCCESS");
						orderPayments.setPaymentAmount(fetchedProduct.get().getProductPrice());
						OrderPayments createdPayment = orderPaymentRepository.save(orderPayments);
						
						InventoryDetails inventoryPayload = new InventoryDetails();
						inventoryPayload.setProductId(fetchedOrder.get().getProduct().getProductId());
						inventoryPayload.setProductQuantity(1);
						log.info("Inside makeOrderPaymentService. inventoryPayload: " + inventoryPayload.toString());
						webClient.post().uri("/ecomm-inventory/api/v1/inventory/update/decrease")
										.bodyValue(inventoryPayload)
										.retrieve()
										.bodyToMono(String.class).block();
		
						CustomerWallet walletPayload = new CustomerWallet();
						walletPayload.setCustomerId(fetchedOrder.get().getCustomer().getCustomerId());
						walletPayload.setWalletBalance(fetchedProduct.get().getProductPrice());
						log.info("Inside makeOrderPaymentService. walletPayload: " + walletPayload.toString());
						customerWalletService.updateWalletBalanceService(walletPayload, "decrease");
						
						//TODO: Send message to kafka to mark order as confirmed in the order processing microservice.
						return createdPayment;
					}
					else {
						orderPayments.setPaymentStatus("FAILURE");
						orderPayments.setMessage((fetchedInventory.get().getProductQuantity() <= 0) ? "Product is out of stock" : "Customer wallet balance is low.");
						OrderPayments failedPayment = orderPaymentRepository.save(orderPayments);
						
						//TODO: Send message to kafka to mark order as cancelled in the order processing microservice.
						
						return failedPayment;
					}
				}
				else {
					throw new Exception("Product or wallet does not exist.");
				}
				
			}
			else {
				throw new Exception("Invalid Order id.");
			}
		}
		else {
			throw new Exception("Payment already done for this order.");
		}
	}
}
