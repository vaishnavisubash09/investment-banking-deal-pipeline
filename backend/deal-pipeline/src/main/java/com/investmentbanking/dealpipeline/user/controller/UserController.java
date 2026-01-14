package com.investmentbanking.dealpipeline.user.controller;

import com.investmentbanking.dealpipeline.user.dto.UserResponseDTO;
import com.investmentbanking.dealpipeline.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponseDTO me() {
        return userService.getCurrentUser();
    }
}
