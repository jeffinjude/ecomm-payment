package com.jeffinjude.payment.entities;

public class InventoryDetails {
	
	private int inventoryId;
	private int productId;
	private double productQuantity;
	private String message;
	
	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(double productQuantity) {
		this.productQuantity = productQuantity;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "InventoryDetails [inventoryId=" + inventoryId + ", productId=" + productId + ", productQuantity="
				+ productQuantity + "]";
	}
	
}
