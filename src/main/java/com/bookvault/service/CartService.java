package com.bookvault.service;
import java.util.List;
import com.bookvault.dto.CartDTO;

public interface CartService {
    boolean addItem(CartDTO cartItem);
    List<CartDTO> viewCart(int userId);
    boolean removeItem(int cartId);
    boolean clearCart(int userId);
}