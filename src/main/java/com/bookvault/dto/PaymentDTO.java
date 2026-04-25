package com.bookvault.dto;

public class PaymentDTO {
    private int paymentId;
    private int orderId;
    private String paymentMethod; // UPI, Credit Card, etc.
    private String transactionId; // A fake string we'll generate like "TXN-98765"
    private String status; // "SUCCESS" or "FAILED"
	public int getPaymentId() {
		return paymentId;
	}
	public int getOrderId() {
		return orderId;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public String getStatus() {
		return status;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public void setStatus(String status) {
		this.status = status;
	}

    
}