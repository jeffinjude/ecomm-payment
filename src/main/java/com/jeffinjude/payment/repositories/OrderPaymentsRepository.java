package com.jeffinjude.payment.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jeffinjude.payment.entities.OrderPayments;

public interface OrderPaymentsRepository extends JpaRepository<OrderPayments, Integer> {
	
	Optional<List<OrderPayments>> findByOrderId(int orderId);
}
