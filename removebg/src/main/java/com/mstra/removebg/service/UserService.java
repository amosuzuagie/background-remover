package com.mstra.removebg.service;

import com.mstra.removebg.dto.UserDTO;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);

    UserDTO getUserByClerkId(String clerkId);

    void deleteUserByClerkId(String clerkId);
}
