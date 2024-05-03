package com.jeffinjude.payment.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.jeffinjude.payment.entities.CustomerWallet;

public interface CustomerWalletRepository extends JpaRepository<CustomerWallet, Integer> {
	
	@Modifying
	@Query("update CustomerWallet cw set cw.walletBalance = :walletBalance where cw.customerId = :customerId")
	int updateWalletBalance(@Param("walletBalance") double walletBalance, @Param("customerId") int customerId);
	
	Optional<CustomerWallet> findByCustomerId(int customerId);
}
