package com.bookvault.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bookvault.dao.CartDAO;
import com.bookvault.dto.CartDTO;
import com.bookvault.utility.DbConnection;

public class CartDAOImpl implements CartDAO {

    @Override
    public boolean addToCart(CartDTO cartItem) {
        
        String sql = "INSERT INTO cart (User_id, Book_id, Quantity) VALUES (?, ?, ?)";
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, cartItem.getUserId());
            ps.setInt(2, cartItem.getBookId());
            ps.setInt(3, cartItem.getQuantity());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public List<CartDTO> getCartByUserId(int userId) {
        List<CartDTO> cartList = new ArrayList<>();
        
        
        String sql = "SELECT c.Cart_id, c.User_id, c.Book_id, c.Quantity, b.Title, b.Price " +
                     "FROM cart c " +
                     "INNER JOIN books b ON c.Book_id = b.Book_id " +
                     "WHERE c.User_id = ?";
                     
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                CartDTO cart = new CartDTO();
                // Raw Cart Data
                cart.setCartId(rs.getInt("Cart_id"));
                cart.setUserId(rs.getInt("User_id"));
                cart.setBookId(rs.getInt("Book_id"));
                cart.setQuantity(rs.getInt("Quantity"));
                
                // Joined Book Data (For display purposes)
                cart.setBookTitle(rs.getString("Title"));
                cart.setBookPrice(rs.getDouble("Price"));
                
                cartList.add(cart);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return cartList;
    }

    @Override
    public boolean removeFromCart(int cartId) {
        String sql = "DELETE FROM cart WHERE Cart_id = ?";
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE User_id = ?";
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    
    @Override
    public List<CartDTO> getAllCarts() {
        List<CartDTO> cartList = new ArrayList<>();
        // No WHERE clause! Pulls every item in every cart.
        String sql = "SELECT c.Cart_id, c.User_id, c.Book_id, c.Quantity, b.Title, b.Price " +
                     "FROM cart c " +
                     "INNER JOIN books b ON c.Book_id = b.Book_id " +
                     "ORDER BY c.User_id";
                     
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                CartDTO cart = new CartDTO();
                cart.setCartId(rs.getInt("Cart_id"));
                cart.setUserId(rs.getInt("User_id")); // Crucial for Admin to know WHOSE cart it is
                cart.setBookId(rs.getInt("Book_id"));
                cart.setQuantity(rs.getInt("Quantity"));
                cart.setBookTitle(rs.getString("Title"));
                cart.setBookPrice(rs.getDouble("Price"));
                cartList.add(cart);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return cartList;
    }
}