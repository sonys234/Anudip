package com.bookvault.service.impl;
import com.bookvault.dao.UserDAO;
import com.bookvault.dao.impl.UserDAOImpl;
import com.bookvault.dto.UserDTO;
import com.bookvault.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean registerUser(UserDTO user) {
        // Business logic: Ensure emails are always saved in lowercase to prevent duplicate login errors
        user.setEmail(user.getEmail().toLowerCase());
        return userDAO.insertUserToDB(user);
    }
}