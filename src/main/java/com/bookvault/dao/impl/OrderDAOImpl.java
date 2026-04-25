package com.bookvault.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; // Required for the generated keys feature
import java.util.ArrayList;
import java.util.List;

import com.bookvault.dao.OrderDAO;
import com.bookvault.dto.OrderDTO;
import com.bookvault.utility.DbConnection;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public int placeOrder(OrderDTO order) {
        int generatedOrderId = -1; // Default to -1 (Failure state)
        
        
        String sql = "INSERT INTO orders (User_id, Total_bill) VALUES (?, ?)";

        try (Connection con = DbConnection.establishConnection();
            
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalBill());
            
            int affectedRows = ps.executeUpdate();

            
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedOrderId = rs.getInt(1); // Extracts the newly minted Order_id
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        
        return generatedOrderId; // We return the new ID to use it for the Payment!
    }

    @Override
    public List<OrderDTO> getOrderHistory(int userId) {
        List<OrderDTO> orderList = new ArrayList<>();
        // Fetching the most recent orders first
        String sql = "SELECT * FROM orders WHERE User_id = ? ORDER BY Order_date DESC";
        
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                OrderDTO order = new OrderDTO();
                order.setOrderId(rs.getInt("Order_id"));
                order.setUserId(rs.getInt("User_id"));
                order.setTotalBill(rs.getDouble("Total_bill"));
                order.setOrderDate(rs.getTimestamp("Order_date"));
                
                orderList.add(order);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return orderList;
    }
    
    @Override
    public boolean cancelOrder(int orderId, int userId) {
        boolean isCancelled = false;
        
        // Step 1: Clear the payment record first to satisfy Foreign Key constraints
        String deletePaymentSql = "DELETE FROM payments WHERE Order_id = ?";
        
        // Step 2: Delete the order, strictly ensuring it belongs to this user
        String deleteOrderSql = "DELETE FROM orders WHERE Order_id = ? AND User_id = ?";

        try (Connection con = DbConnection.establishConnection()) {
            
            // Execute Payment Deletion
            try (PreparedStatement psPay = con.prepareStatement(deletePaymentSql)) {
                psPay.setInt(1, orderId);
                psPay.executeUpdate(); 
            }

            // Execute Order Deletion
            try (PreparedStatement psOrd = con.prepareStatement(deleteOrderSql)) {
                psOrd.setInt(1, orderId);
                psOrd.setInt(2, userId);
                
                int rowsAffected = psOrd.executeUpdate();
                if (rowsAffected > 0) {
                    isCancelled = true;
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        
        return isCancelled;
    }
    
    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orderList = new ArrayList<>();
        // Pulls all orders, sorting by most recent first
        String sql = "SELECT * FROM orders ORDER BY Order_date DESC";
        
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                OrderDTO order = new OrderDTO();
                order.setOrderId(rs.getInt("Order_id"));
                order.setUserId(rs.getInt("User_id")); // Shows who placed it
                order.setTotalBill(rs.getDouble("Total_bill"));
                order.setOrderDate(rs.getTimestamp("Order_date"));
                orderList.add(order);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return orderList;
    }
}