package com.bookvault.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bookvault.controller.CartController;
import com.bookvault.controller.OrderController;
import com.bookvault.controller.PaymentController;
import com.bookvault.dao.BookDAO;
import com.bookvault.dao.OrderItemDAO;
import com.bookvault.dao.impl.BookDAOImpl;
import com.bookvault.dao.impl.OrderItemDAOImpl;
import com.bookvault.dto.CartDTO;
import com.bookvault.dto.OrderDTO;
import com.bookvault.dto.OrderItemDTO;
import com.bookvault.dto.PaymentDTO;

public class OrderDashBoard {
    private Scanner sc = new Scanner(System.in);
    
    // Using the Controllers instead of DAOs
    private CartController cartController = new CartController();
    private OrderController orderController = new OrderController();
    private PaymentController paymentController = new PaymentController();

    public OrderDashBoard() {
        showOrderMenu();
    }

    public void showOrderMenu() {
        int choice = 0;
        while (choice != 4) { 
            System.out.println("\n======================================");
            System.out.println("=== || 📦 CHECKOUT & ORDERS || ===");
            System.out.println("======================================");
            System.out.println("1. Proceed to Checkout");
            System.out.println("2. View Order History");
            System.out.println("3. Cancel an Order"); 
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: processCheckout(); break;
                case 2: viewHistory(); break;
                case 3: cancelOrder(); break; 
                case 4: System.out.println("Returning to Main Menu..."); break;
                default: System.out.println("⚠️ Invalid choice. Try again.");
            }
        }
    }

    private void processCheckout() {
        int userId = UserDashBoard.currentUser.getUserId();
        List<CartDTO> myCart = cartController.viewUserCart(userId);

        if (myCart.isEmpty()) {
            System.out.println("❌ Your cart is empty. Add some books before checking out!");
            return;
        }

        // 1. Calculate Total
        double grandTotal = 0.0;
        for (CartDTO c : myCart) {
            grandTotal += (c.getBookPrice() * c.getQuantity());
        }

        System.out.println("\n--- CHECKOUT SUMMARY ---");
        System.out.println("Total Amount Due: ₹" + grandTotal);
        System.out.print("Do you want to place the order? (YES/NO): ");
        if (!sc.next().equalsIgnoreCase("YES")) {
            System.out.println("Checkout cancelled.");
            return;
        }

        // 2. Create the Order
        OrderDTO newOrder = new OrderDTO();
        newOrder.setUserId(userId);
        newOrder.setTotalBill(grandTotal);
        
        int generatedOrderId = orderController.createOrder(newOrder);

        if (generatedOrderId > 0) {
            System.out.println("✅ Order Created! Order ID: " + generatedOrderId);
            
            // 3. Process the Payment
            System.out.println("\n--- PAYMENT GATEWAY ---");
            System.out.println("Select Method: 1. Credit Card  2. UPI  3. Net Banking");
            System.out.print("Choice: ");
            int payChoice = sc.nextInt();
            String method = (payChoice == 1) ? "Credit Card" : (payChoice == 2) ? "UPI" : "Net Banking";

            PaymentDTO payment = new PaymentDTO();
            payment.setOrderId(generatedOrderId);
            payment.setPaymentMethod(method);

            if (paymentController.executePayment(payment)) {
                
                // --- NEW: DEDUCT INVENTORY & SAVE ORDER ITEMS ---
                BookDAO bookDAO = new BookDAOImpl();
                OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
                List<OrderItemDTO> orderItemsList = new ArrayList<>();
                
                for (CartDTO c : myCart) {
                    // 1. Physically deduct the stock since the payment cleared
                    bookDAO.reduceStock(c.getBookId(), c.getQuantity());
                    
                    // 2. Map the cart item to an OrderItemDTO for the receipt
                    OrderItemDTO item = new OrderItemDTO();
                    item.setOrderId(generatedOrderId);
                    item.setBookId(c.getBookId());
                    item.setQuantity(c.getQuantity());
                    item.setPrice(c.getBookPrice());
                    orderItemsList.add(item);
                }
                
                // 3. Save the itemized receipt to the database
                orderItemDAO.saveOrderItems(orderItemsList);
                // -------------------------------------------------
                
                // 4. Clean Up the Cart!
                cartController.clearUserCart(userId);
                
                System.out.println("\n🎉 PAYMENT SUCCESSFUL! 🎉");
                System.out.println("Your books are being prepared for shipment. Inventory and receipts updated.");
            } else {
                System.out.println("❌ Payment Failed. Please contact support.");
            }
        } else {
            System.out.println("❌ Failed to create order. Please try again later.");
        }
    }

    private void viewHistory() {
        int userId = UserDashBoard.currentUser.getUserId();
        List<OrderDTO> history = orderController.getUserOrderHistory(userId);

        System.out.println("\n--- YOUR ORDER HISTORY ---");
        System.out.println(String.format("%-10s | %-12s | %-20s", "Order ID", "Total Bill", "Date"));
        System.out.println("--------------------------------------------------");
        
        if (history.isEmpty()) {
            System.out.println("You haven't placed any orders yet.");
        } else {
            for (OrderDTO o : history) {
                System.out.println(String.format("%-10d | ₹%-11.2f | %-20s", 
                    o.getOrderId(), o.getTotalBill(), o.getOrderDate().toString()));
            }
        }
    }
    
    private void cancelOrder() {
        System.out.println("\n--- CANCEL ORDER ---");
        viewHistory(); 
        
        System.out.print("\nEnter the Order ID you wish to cancel (or 0 to go back): ");
        int orderId = sc.nextInt();
        
        if (orderId == 0) return;

        System.out.print("Are you sure you want to cancel Order #" + orderId + "? (YES/NO): ");
        if (sc.next().equalsIgnoreCase("YES")) {
            int userId = UserDashBoard.currentUser.getUserId();
            
            // The Service layer now handles the database restocking automatically!
            if (orderController.cancelUserOrder(orderId, userId)) {
                System.out.println("✅ Order #" + orderId + " has been successfully cancelled and refunded.");
                System.out.println("📦 Inventory has been automatically restocked.");
            } else {
                System.out.println("❌ Could not cancel order. Please verify the Order ID is correct.");
            }
        } else {
            System.out.println("Cancellation aborted.");
        }
    }
}