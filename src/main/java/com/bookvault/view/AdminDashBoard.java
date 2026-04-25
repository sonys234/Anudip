package com.bookvault.view;

import java.util.List;
import java.util.Scanner;

import com.bookvault.controller.BookController;
import com.bookvault.dao.BookDAO;
import com.bookvault.dao.CartDAO;
import com.bookvault.dao.OrderDAO;
import com.bookvault.dao.impl.BookDAOImpl;
import com.bookvault.dao.impl.CartDAOImpl;
import com.bookvault.dao.impl.OrderDAOImpl;
import com.bookvault.dto.BookDTO;
import com.bookvault.dto.CartDTO;
import com.bookvault.dto.OrderDTO;

public class AdminDashBoard {
    private Scanner sc = new Scanner(System.in);
    
    private BookDAO bookDAO = new BookDAOImpl();
    private BookController bookController = new BookController();
    private CartDAO cartDAO = new CartDAOImpl();
    private OrderDAO orderDAO = new OrderDAOImpl();

    
    public AdminDashBoard() {
        showAdminMenu();
    }

    public void showAdminMenu() {
        int choice = 0;

        while (choice != 8) {
            System.out.println("\n======================================");
            System.out.println("=== || ADMIN CONTROL CENTER || ===");
            System.out.println("======================================");
            System.out.println("1. Add New Book");
            System.out.println("2. Update Book Price");
            System.out.println("3. Delete Book from Store");
            System.out.println("4. View All Inventory (Quick View)");
            System.out.println("5. View Specific Book Details");
            System.out.println("6. View All Active User Carts"); 
            System.out.println("7. View All Historical Orders"); 
            System.out.println("8. Logout & Return to Main");
            System.out.println("======================================");
            System.out.print("Enter choice (1-8): ");
            
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1: addBook(); break;
                case 2: updatePrice(); break;
                case 3: removeBook(); break;
                case 4: viewInventory(); break;
                case 5: viewBookDetails(); break; // NEW METHOD CALL
                case 6: viewAllCarts(); break; 
                case 7: viewAllOrders(); break; 
                case 8: System.out.println("Admin logged out. Returning to Main..."); break;
                default: System.out.println("⚠️ Invalid choice. Try again.");
            }
        }
    }

    private void viewInventory() {
        List<BookDTO> books = bookDAO.getAllBooks();
        System.out.println("\n--- QUICK VIEW INVENTORY ---");
        System.out.println(String.format("%-5s | %-30s | %-20s | %-8s | %-6s", "ID", "Title", "Author", "Price", "Stock"));
        System.out.println("--------------------------------------------------------------------------------");
        
        if (books.isEmpty()) {
            System.out.println("The vault is currently empty.");
        } else {
            for (BookDTO b : books) {
                // Truncate long strings to keep table neat
                String title = b.getTitle().length() > 28 ? b.getTitle().substring(0, 25) + "..." : b.getTitle();
                String author = b.getAuthor().length() > 18 ? b.getAuthor().substring(0, 15) + "..." : b.getAuthor();
                
                System.out.println(String.format("%-5d | %-30s | %-20s | ₹%-7.2f | %-6d", 
                    b.getBookId(), title, author, b.getPrice(), b.getQuantity()));
            }
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("💡 Hint: Use Option 5 to view full details of any book using its ID!");
        }
    }

    public void viewBookDetails() {
        System.out.print("\nEnter the Book ID to view full details: ");
        int id = sc.nextInt();
        
        BookDTO book = bookDAO.getBookById(id);

        if (book != null) {
            System.out.println("\n======================================");
            System.out.println("          📖 BOOK DETAILS 📖          ");
            System.out.println("======================================");
            System.out.println("Title       : " + book.getTitle());
            System.out.println("Author      : " + book.getAuthor());
            System.out.println("Genre       : " + (book.getGenre() == null ? "N/A" : book.getGenre()));
            System.out.println("Rating      : " + book.getRating() + " / 5.0 ⭐");
            System.out.println("Price       : ₹" + book.getPrice());
            System.out.println("Stock Left  : " + book.getQuantity() + " copies");
            System.out.println("--------------------------------------");
            System.out.println("Description :");
            System.out.println(book.getDescription() == null ? "No description available." : book.getDescription());
            System.out.println("======================================\n");
        } else {
            System.out.println("❌ Error: No book found with ID " + id);
        }
    }

    private void updatePrice() {
        System.out.print("Enter the Book ID you wish to update: ");
        int id = sc.nextInt();
        System.out.print("Enter New Price: ₹");
        double newPrice = sc.nextDouble();

        if (bookDAO.updateBookPrice(id, newPrice)) {
            System.out.println("✅ Price updated successfully for Book ID: " + id);
        } else {
            System.out.println("❌ Error: Could not update. Verify the Book ID exists.");
        }
    }

    private void removeBook() {
        System.out.print("Enter the Book ID to delete: ");
        int id = sc.nextInt();
        System.out.print("⚠️ Are you sure? This cannot be undone. (Type 'YES' to confirm): ");
        String confirm = sc.next();

        if (confirm.equalsIgnoreCase("YES")) {
            if (bookDAO.deleteBook(id)) {
                System.out.println("✅ Book ID " + id + " has been removed from the system.");
            } else {
                System.out.println("❌ Error: Could not delete book. Check if ID exists.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    public void addBook() {
        System.out.println("\n--- Register New Book ---");
        System.out.print("Title: "); String title = sc.nextLine();
        System.out.print("Author: "); String author = sc.nextLine();
        System.out.print("Description: "); String desc = sc.nextLine();
        System.out.print("Genre: "); String genre = sc.nextLine();
        System.out.print("Price: ₹"); double price = sc.nextDouble();
        System.out.print("Quantity: "); int qty = sc.nextInt();
        System.out.print("Rating (0-5): "); double rating = sc.nextDouble();

        BookDTO book = new BookDTO();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(desc);
        book.setGenre(genre);
        book.setPrice(price);
        book.setQuantity(qty);
        book.setRating(rating);

        if (bookController.insertBook(book)) {
            System.out.println("✅ Book added to database successfully!");
        } else {
            System.out.println("❌ Validation or Database error. Check your inputs.");
        }
    }

    private void viewAllCarts() {
        List<CartDTO> allCarts = cartDAO.getAllCarts();
        System.out.println("\n--- GLOBAL CART MONITOR ---");
        System.out.println(String.format("%-8s | %-8s | %-25s | %-6s", "Cart ID", "User ID", "Book Title", "Qty"));
        System.out.println("---------------------------------------------------------------");
        
        if (allCarts.isEmpty()) {
            System.out.println("No users currently have items in their carts.");
        } else {
            for (CartDTO c : allCarts) {
                System.out.println(String.format("%-8d | %-8d | %-25s | %-6d", 
                    c.getCartId(), c.getUserId(), c.getBookTitle(), c.getQuantity()));
            }
        }
    }

    private void viewAllOrders() {
        List<OrderDTO> allOrders = orderDAO.getAllOrders();
        System.out.println("\n--- GLOBAL ORDER HISTORY ---");
        System.out.println(String.format("%-8s | %-8s | %-12s | %-20s", "Order ID", "User ID", "Total Bill", "Date"));
        System.out.println("----------------------------------------------------------------");
        
        if (allOrders.isEmpty()) {
            System.out.println("No orders have been placed in the system yet.");
        } else {
            for (OrderDTO o : allOrders) {
                System.out.println(String.format("%-8d | %-8d | ₹%-11.2f | %-20s", 
                    o.getOrderId(), o.getUserId(), o.getTotalBill(), o.getOrderDate().toString()));
            }
        }
    }
}