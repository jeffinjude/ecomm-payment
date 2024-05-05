package com.jeffinjude.payment.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	    info = @Info(
	        title = "Payment Service API",
	        description = "This api manages the payment of orders and customer wallet.",
	        summary = "This api can create wallet for a customer, get the balance available, increase balance, decrease balance and make an order payment.",
	        contact = @Contact(
	        			name = "Jeffin Pulickal",
	        			email = "test@test.com"
	        		),
	        version = "v1"
	    )
	)
public class SwaggerConfig {
	//Access swagger at http://localhost:8095/swagger-ui/index.html
}
