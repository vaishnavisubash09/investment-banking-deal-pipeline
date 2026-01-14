package com.investmentbanking.dealpipeline.user.controller;

import com.investmentbanking.dealpipeline.user.dto.UpdateUserStatusRequest;
import com.investmentbanking.dealpipeline.user.dto.UserCreateRequestDTO;
import com.investmentbanking.dealpipeline.user.dto.UserResponseDTO;
import com.investmentbanking.dealpipeline.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserCreateRequestDTO dto) {
        return userService.createUser(dto);
    }

    @GetMapping
    public List<UserResponseDTO> getUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}/status")
    public void updateStatus(
            @PathVariable String id,
            @RequestBody UpdateUserStatusRequest request
    ) {
        userService.updateUserStatus(id, request.active());
    }
}
