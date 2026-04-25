package com.bookvault.service.impl;
import java.util.UUID;
import com.bookvault.dao.PaymentDAO;
import com.bookvault.dao.impl.PaymentDAOImpl;
import com.bookvault.dto.PaymentDTO;
import com.bookvault.service.PaymentService;

public class PaymentServiceImpl implements PaymentService {
    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public boolean processPayment(PaymentDTO payment) {
        // BUSINESS LOGIC: Generate secure Transaction ID here
        String generatedTxnId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        payment.setTransactionId(generatedTxnId);
        payment.setStatus("SUCCESS");
        
        return paymentDAO.processPayment(payment);
    }
}