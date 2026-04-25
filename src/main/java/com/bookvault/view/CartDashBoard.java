package com.bookvault.view;

import java.util.List;
import java.util.Scanner;

import com.bookvault.controller.CartController;
import com.bookvault.dao.BookDAO;
import com.bookvault.dao.impl.BookDAOImpl;
import com.bookvault.dto.BookDTO;
import com.bookvault.dto.CartDTO;

public class CartDashBoard {
    private Scanner sc = new Scanner(System.in);
    
    
    private CartController cartController = new CartController();
    private BookDAO bookDAO = new BookDAOImpl(); 

    
    public CartDashBoard() {
        showCartMenu();
    }

    public void showCartMenu() {
        int choice = 0;
        while (choice != 5) {
            System.out.println("\n======================================");
            System.out.println("=== || 🛒 YOUR SHOPPING CART || ===");
            System.out.println("======================================");
            System.out.println("1. View Cart Details & Total");
            System.out.println("2. Add Book to Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. Clear Cart (Checkout Prep)");
            System.out.println("5. Back to Storefront");
            System.out.println("======================================");
            System.out.print("Enter choice (1-5): ");
            
            choice = sc.nextInt();

            switch (choice) {
                case 1: viewCart(); break;
                case 2: addToCart(); break;
                case 3: removeFromCart(); break;
                case 4: clearCart(); break;
                case 5: System.out.println("Returning to Storefront..."); break;
                default: System.out.println("⚠️ Invalid choice. Try again.");
            }
        }
    }

    private void viewCart() {
        int currentUserId = UserDashBoard.currentUser.getUserId();
        List<CartDTO> myCart = cartController.viewUserCart(currentUserId);
        
        System.out.println("\n--- CART CONTENTS ---");
        System.out.println(String.format("%-8s | %-20s | %-8s | %-8s | %-10s", "Cart ID", "Book Title", "Price", "Qty", "Subtotal"));
        System.out.println("----------------------------------------------------------------------");
        
        if (myCart.isEmpty()) {
            System.out.println("Your cart is completely empty.");
        } else {
            double grandTotal = 0.0;
            for (CartDTO c : myCart) {
                double subTotal = c.getBookPrice() * c.getQuantity();
                grandTotal += subTotal;
                
                System.out.println(String.format("%-8d | %-20s | ₹%-7.2f | %-8d | ₹%-9.2f", 
                    c.getCartId(), c.getBookTitle(), c.getBookPrice(), c.getQuantity(), subTotal));
            }
            System.out.println("----------------------------------------------------------------------");
            System.out.println(String.format("%49s: ₹%-9.2f", "GRAND TOTAL", grandTotal));
        }
    }

    private void addToCart() {
        
        System.out.println("\n--- AVAILABLE INVENTORY ---");
        System.out.println(String.format("%-5s | %-25s | %-6s", "ID", "Title", "Stock"));
        System.out.println("--------------------------------------------");
        
        List<BookDTO> allBooks = bookDAO.getAllBooks();
        for (BookDTO b : allBooks) {
            System.out.println(String.format("%-5d | %-25s | %-6d", b.getBookId(), b.getTitle(), b.getQuantity()));
        }
        System.out.println("--------------------------------------------");

        System.out.print("\nEnter the Book ID you want to buy: ");
        int bookId = sc.nextInt();
        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        CartDTO cartItem = new CartDTO();
        cartItem.setUserId(UserDashBoard.currentUser.getUserId()); 
        cartItem.setBookId(bookId);
        cartItem.setQuantity(qty);

        
        if (cartController.addItemToCart(cartItem)) {
            System.out.println("✅ Book successfully added to your cart!");
        } 
    }

    private void removeFromCart() {
        System.out.print("Enter the Cart ID of the item to remove: ");
        int cartId = sc.nextInt();

        if (cartController.removeCartItem(cartId)) {
            System.out.println("✅ Item removed from your cart.");
        } else {
            System.out.println("❌ Failed to remove item. Check the Cart ID.");
        }
    }

    private void clearCart() {
        System.out.print("Are you sure you want to empty your cart? (YES/NO): ");
        if (sc.next().equalsIgnoreCase("YES")) {
            int currentUserId = UserDashBoard.currentUser.getUserId();
            if (cartController.clearUserCart(currentUserId)) {
                System.out.println("🗑️ Cart cleared successfully.");
            } else {
                System.out.println("❌ Cart is already empty or an error occurred.");
            }
        }
    }
}