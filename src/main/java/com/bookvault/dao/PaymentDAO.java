package com.bookvault.dao;
import com.bookvault.dto.PaymentDTO;

public interface PaymentDAO {
    boolean processPayment(PaymentDTO payment);
}