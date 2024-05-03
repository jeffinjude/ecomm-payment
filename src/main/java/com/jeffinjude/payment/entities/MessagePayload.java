package com.jeffinjude.payment.entities;

public class MessagePayload {
	private int orderId;
	private String orderStatus;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	@Override
	public String toString() {
		return "MessagePayload [orderId=" + orderId + ", orderStatus=" + orderStatus + "]";
	}
}
