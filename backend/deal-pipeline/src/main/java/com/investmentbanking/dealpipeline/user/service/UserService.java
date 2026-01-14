package com.investmentbanking.dealpipeline.user.service;

import com.investmentbanking.dealpipeline.user.dto.UserCreateRequestDTO;
import com.investmentbanking.dealpipeline.user.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserCreateRequestDTO dto);
    List<UserResponseDTO> getAllUsers();
    void updateUserStatus(String userId, boolean active);
    UserResponseDTO getCurrentUser();
}
