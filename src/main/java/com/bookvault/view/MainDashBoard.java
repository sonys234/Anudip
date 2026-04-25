package com.bookvault.view;

import java.util.Scanner;
import com.bookvault.dao.UserDAO;
import com.bookvault.dao.impl.UserDAOImpl;
import com.bookvault.dto.UserDTO;
/**
 * sony singh
 */
public class MainDashBoard {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n======================================");
            System.out.println("=== || WELCOME TO BOOK VAULT || ===");
            System.out.println("======================================");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Customer");
            System.out.println("3. Register New Account");
            System.out.println("4. Exit");
            System.out.println("======================================");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    adminLogin(sc);
                    break;
                case 2:
                    customerLogin(sc);
                    break;
                case 3:
                    
                    new UserDashBoard().registerUser(); 
                    break;
                case 4:
                    System.out.println("Exiting Application... Goodbye!");
                    break;
                default:
                    System.out.println("⚠️ Invalid choice.");
            }
        } while (choice != 4);
        
        sc.close();
    }

    private static void adminLogin(Scanner sc) {
        System.out.println("\n--- ADMIN PORTAL ---");
        System.out.print("Admin Email: ");
        String email = sc.next();
        System.out.print("Password: ");
        String pass = sc.next();

        // Hardcoded single Admin credential constraint
        if (email.equals("admin@bookvault.com") && pass.equals("admin123")) {
            System.out.println("✅ Security Clearance Accepted. Welcome, Admin.");
            new AdminDashBoard(); // Launch Admin Module
        } else {
            System.out.println("❌ Access Denied. Invalid Admin Credentials.");
        }
    }

    private static void customerLogin(Scanner sc) {
        System.out.println("\n--- CUSTOMER LOGIN ---");
        System.out.print("Email: ");
        String email = sc.next();
        System.out.print("Password: ");
        String pass = sc.next();

        UserDAO userDAO = new UserDAOImpl();
        if (userDAO.loginUser(email, pass)) {
            System.out.println("\n✅ Login Successful!");
            
            
            UserDashBoard.currentUser = userDAO.getUserByEmail(email); 
            
            
            new UserDashBoard().viewStore(); 
        } else {
            System.out.println("❌ Invalid Email or Password.");
        }
    }
}