package com.bookvault.service;
import com.bookvault.dto.PaymentDTO;

public interface PaymentService {
    boolean processPayment(PaymentDTO payment);
}