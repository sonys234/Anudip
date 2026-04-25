package com.bookvault.service;
import com.bookvault.dto.UserDTO;

public interface UserService {
    boolean registerUser(UserDTO user);
}