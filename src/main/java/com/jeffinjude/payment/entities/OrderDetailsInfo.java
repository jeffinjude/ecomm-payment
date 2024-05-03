package com.jeffinjude.payment.entities;

public class OrderDetailsInfo {
	Customer customer;
	Product product;
	String orderStatus;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	@Override
	public String toString() {
		return "OrderDetailsInfo [customer=" + customer + ", product=" + product + ", orderStatus=" + orderStatus + "]";
	}
	
}
