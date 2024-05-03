package com.jeffinjude.payment.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "order_payments")
public class OrderPayments {
	
	@Id
    @Column(name = "payment_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int paymentId;
	
	@Column(name = "order_id", nullable = false)
	private int orderId;
	
	@Column(name = "payment_amount", nullable = false)
	private double paymentAmount;
	
	@Column(name = "payment_status", nullable = false)
	private String paymentStatus;
	
	@Transient
	private String message;

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "OrderPayments [paymentId=" + paymentId + ", orderId=" + orderId + ", paymentAmount=" + paymentAmount
				+ ", paymentStatus=" + paymentStatus + "]";
	}
	
}
