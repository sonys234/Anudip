package com.bookvault.controller;
import com.bookvault.dto.UserDTO;
import com.bookvault.service.UserService;
import com.bookvault.service.impl.UserServiceImpl;

public class UserController {
    private UserService userService = new UserServiceImpl();

    public boolean insertUser(UserDTO user) {
        // Basic Validation
        if (!user.getEmail().contains("@")) {
            System.out.println("⚠️ Validation Error: Invalid email format.");
            return false;
        }
        if (user.getPassword().length() < 6) {
            System.out.println("⚠️ Validation Error: Password must be at least 6 characters.");
            return false;
        }
        return userService.registerUser(user);
    }
}