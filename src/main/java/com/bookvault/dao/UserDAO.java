package com.bookvault.dao;

import com.bookvault.dto.UserDTO;

public interface UserDAO {
    boolean insertUserToDB(UserDTO user);
    boolean loginUser(String email, String password);
    UserDTO getUserByEmail(String email);
    boolean updateUserProfile(UserDTO user);
    boolean changePassword(int userId, String oldPassword, String newPassword);
    boolean deleteUserAccount(int userId);
}