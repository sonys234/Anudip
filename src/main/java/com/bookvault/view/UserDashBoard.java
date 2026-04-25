package com.bookvault.view;

import java.util.List;
import java.util.Scanner;

import com.bookvault.controller.UserController;
import com.bookvault.dao.BookDAO;
import com.bookvault.dao.UserDAO;
import com.bookvault.dao.impl.BookDAOImpl;
import com.bookvault.dao.impl.UserDAOImpl;
import com.bookvault.dto.BookDTO;
import com.bookvault.dto.UserDTO;

public class UserDashBoard {
    
    public static UserDTO currentUser = null;
    private Scanner sc = new Scanner(System.in);

    
    public UserDashBoard() {
        
    }

    public void registerUser() {
        System.out.println("\n--- USER REGISTRATION ---");
        System.out.print("Enter your First Name: ");
        String fName = sc.next();
        
        System.out.print("Enter your Last Name: ");
        String lName = sc.next();
        
        System.out.print("Enter your Email ID: ");
        String emailId = sc.next();
        
        System.out.print("Enter your Contact Number: ");
        String contact = sc.next();
        
        System.out.print("Enter your Password: ");
        String password = sc.next();
        
        UserDTO myUser = new UserDTO();
        myUser.setFirstName(fName);
        myUser.setLastName(lName);
        myUser.setEmail(emailId);
        myUser.setContact(contact);
        myUser.setPassword(password);
        
        UserController userController = new UserController();
        boolean isSuccess = userController.insertUser(myUser);

        if (isSuccess) {
            System.out.println("✅ Registration Successful! You can now login from the Main Menu.");
        } else {
            System.out.println("❌ Registration Failed. Email might already exist.");
        }
    }

    public void viewStore() {
        int choice = 0;

        // Loop changed to 8
        while (choice != 8 && currentUser != null) {
            System.out.println("\n======================================");
            System.out.println("Welcome to the Storefront, " + currentUser.getFirstName() + "!");
            System.out.println("======================================");
            System.out.println("1. Browse All Books (Quick View)");
            System.out.println("2. View Specific Book Details");
            System.out.println("3. View My Cart");
            System.out.println("4. Checkout & Orders"); 
            System.out.println("5. Update Profile");
            System.out.println("6. Change Password");
            System.out.println("7. Delete Account");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    displayAllBooks();
                    break;
                case 2:
                    viewBookDetails();
                    break;
                case 3:
                    new CartDashBoard(); 
                    break;
                case 4:
                    new OrderDashBoard(); 
                    break;
                case 5:
                    updateProfile();
                    break;
                case 6:
                    changePassword();
                    break;
                case 7:
                    deleteAccount();
                    break;
                case 8: 
                    System.out.println("👋 Logged out successfully. Visit again!");
                    currentUser = null; 
                    break;
                default:
                    System.out.println("⚠️ Invalid choice. Please try again.");
            }
        }
    }
    
    public void displayAllBooks() {
        BookDAO bookDAO = new BookDAOImpl();
        List<BookDTO> allBooks = bookDAO.getAllBooks();
        
        System.out.println("\n--- QUICK VIEW INVENTORY ---");
        System.out.println(String.format("%-5s | %-30s | %-20s | %-8s", "ID", "Title", "Author", "Price"));
        System.out.println("-----------------------------------------------------------------------");
        
        if(allBooks.isEmpty()) {
            System.out.println("No books available in the vault yet.");
        } else {
            for (BookDTO b : allBooks) {
                
                String title = b.getTitle().length() > 28 ? b.getTitle().substring(0, 25) + "..." : b.getTitle();
                String author = b.getAuthor().length() > 18 ? b.getAuthor().substring(0, 15) + "..." : b.getAuthor();
                
                System.out.println(String.format("%-5d | %-30s | %-20s | ₹%-7.2f", 
                    b.getBookId(), title, author, b.getPrice()));
            }
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("💡 Hint: Use Option 2 to view full details of any book using its ID!");
        }
    }

    public void viewBookDetails() {
        System.out.print("\nEnter the Book ID to view full details: ");
        int id = sc.nextInt();
        
        BookDAO bookDAO = new BookDAOImpl();
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

    public void updateProfile() {
        System.out.println("\n--- UPDATE PROFILE ---");
        System.out.println("Current Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
        
        System.out.print("Enter New First Name: ");
        String fName = sc.next();
        System.out.print("Enter New Last Name: ");
        String lName = sc.next();
        System.out.print("Enter New Contact Number: ");
        String contact = sc.next();

        UserDTO updatedUser = new UserDTO();
        updatedUser.setUserId(currentUser.getUserId());
        updatedUser.setFirstName(fName);
        updatedUser.setLastName(lName);
        updatedUser.setContact(contact);

        UserDAO dao = new UserDAOImpl();
        if (dao.updateUserProfile(updatedUser)) {
            System.out.println("✅ Profile updated successfully!");
            // Update session data
            currentUser.setFirstName(fName);
            currentUser.setLastName(lName);
            currentUser.setContact(contact);
        } else {
            System.out.println("❌ Failed to update profile.");
        }
    }

    public void changePassword() {
        System.out.println("\n--- CHANGE PASSWORD ---");
        System.out.print("Enter Current Password: ");
        String oldPass = sc.next();
        
        System.out.print("Enter New Password: ");
        String newPass = sc.next();
        
        System.out.print("Confirm New Password: ");
        String confirmPass = sc.next();

        if (!newPass.equals(confirmPass)) {
            System.out.println("❌ Error: New passwords do not match!");
            return;
        }

        UserDAO dao = new UserDAOImpl();
        if (dao.changePassword(currentUser.getUserId(), oldPass, newPass)) {
            System.out.println("✅ Password changed successfully!");
        } else {
            System.out.println("❌ Error: Current password was incorrect.");
        }
    }

    public void deleteAccount() {
        System.out.println("\n⚠️ WARNING: DELETE ACCOUNT ⚠️");
        System.out.println("This action is permanent and cannot be undone.");
        System.out.print("Are you absolutely sure? (Type 'YES' to confirm): ");
        String confirm = sc.next();

        if (confirm.equalsIgnoreCase("YES")) {
            UserDAO dao = new UserDAOImpl();
            if (dao.deleteUserAccount(currentUser.getUserId())) {
                System.out.println("✅ Account deleted. We're sad to see you go.");
                currentUser = null; 
            } else {
                System.out.println("❌ Error: Could not delete account. Make sure your cart/orders are clear first.");
            }
        } else {
            System.out.println("Phew! Account deletion cancelled.");
        }
    }
}