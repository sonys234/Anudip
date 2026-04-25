package com.bookvault.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bookvault.dao.OrderItemDAO;
import com.bookvault.dto.OrderItemDTO;
import com.bookvault.utility.DbConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    @Override
    public boolean saveOrderItems(List<OrderItemDTO> items) {
        String sql = "INSERT INTO order_items (Order_id, Book_id, Quantity, Price) VALUES (?, ?, ?, ?)";
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // We loop through the cart items and add them to a "Batch" to save them all at once!
            for (OrderItemDTO item : items) {
                ps.setInt(1, item.getOrderId());
                ps.setInt(2, item.getBookId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getPrice());
                ps.addBatch(); 
            }
            ps.executeBatch(); // Executes all inserts in one go
            return true;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public List<OrderItemDTO> getItemsByOrderId(int orderId) {
        List<OrderItemDTO> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE Order_id = ?";
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItemDTO item = new OrderItemDTO();
                item.setOrderItemId(rs.getInt("Item_id"));
                item.setOrderId(rs.getInt("Order_id"));
                item.setBookId(rs.getInt("Book_id"));
                item.setQuantity(rs.getInt("Quantity"));
                item.setPrice(rs.getDouble("Price"));
                items.add(item);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return items;
    }

    @Override
    public boolean deleteItemsByOrderId(int orderId) {
        String sql = "DELETE FROM order_items WHERE Order_id = ?";
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}