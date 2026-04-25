package com.bookvault.controller;
import com.bookvault.dto.PaymentDTO;
import com.bookvault.service.PaymentService;
import com.bookvault.service.impl.PaymentServiceImpl;

public class PaymentController {
    private PaymentService paymentService = new PaymentServiceImpl();

    public boolean executePayment(PaymentDTO payment) {
        if (payment.getOrderId() <= 0) {
            System.out.println("⚠️ Validation Error: Invalid Order ID for payment.");
            return false;
        }
        return paymentService.processPayment(payment);
    }
}