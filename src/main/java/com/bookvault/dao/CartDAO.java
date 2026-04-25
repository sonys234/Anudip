package com.bookvault.dao;

import java.util.List;
import com.bookvault.dto.CartDTO;

public interface CartDAO {
    // 1. Add an item to the cart
    boolean addToCart(CartDTO cartItem);
    
    // 2. View all items in a specific user's cart
    List<CartDTO> getCartByUserId(int userId);
    
    // 3. Remove a specific item from the cart
    boolean removeFromCart(int cartId);
    
    // 4. Clear the whole cart (used after checkout)
    boolean clearCart(int userId);
    
 // NEW: For Admin to monitor all active carts
    List<CartDTO> getAllCarts();
}