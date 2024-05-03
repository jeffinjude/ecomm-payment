package com.jeffinjude.payment.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "customer_wallet")
public class CustomerWallet {
	@Id
    @Column(name = "wallet_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int walletId;
	
	@Column(name = "customer_id", nullable = false)
	private int customerId;
	
	@Column(name = "wallet_balance", nullable = false)
	private double walletBalance;
	
	@Transient
	private String message;
	
	public int getWalletId() {
		return walletId;
	}

	public void setWalletId(int walletId) {
		this.walletId = walletId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CustomerWallet [walletId=" + walletId + ", customerId=" + customerId + ", walletBalance="
				+ walletBalance + "]";
	}
	
}
