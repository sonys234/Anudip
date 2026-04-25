package com.bookvault.controller;
import java.util.List;
import com.bookvault.dto.CartDTO;
import com.bookvault.service.CartService;
import com.bookvault.service.impl.CartServiceImpl;

public class CartController {
    private CartService cartService = new CartServiceImpl();

    public boolean addItemToCart(CartDTO cartItem) {
        // Validation Only
        if (cartItem.getQuantity() <= 0) {
            System.out.println("⚠️ Validation Error: Quantity must be at least 1.");
            return false;
        }
        return cartService.addItem(cartItem);
    }

    public List<CartDTO> viewUserCart(int userId) { return cartService.viewCart(userId); }
    public boolean removeCartItem(int cartId) { return cartService.removeItem(cartId); }
    public boolean clearUserCart(int userId) { return cartService.clearCart(userId); }
}