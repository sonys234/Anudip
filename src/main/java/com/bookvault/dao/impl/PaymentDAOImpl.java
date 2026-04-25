package com.bookvault.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.bookvault.dao.PaymentDAO;
import com.bookvault.dto.PaymentDTO;
import com.bookvault.utility.DbConnection;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public boolean processPayment(PaymentDTO payment) {
        String sql = "INSERT INTO payments (Order_id, Payment_method, Transaction_id, Status) VALUES (?, ?, ?, ?)";
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, payment.getOrderId());
            ps.setString(2, payment.getPaymentMethod());
            ps.setString(3, payment.getTransactionId());
            ps.setString(4, payment.getStatus());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return false;
    }
}