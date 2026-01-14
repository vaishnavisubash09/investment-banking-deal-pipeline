package com.investmentbanking.dealpipeline.user.service;

import com.investmentbanking.dealpipeline.user.dto.UserCreateRequestDTO;
import com.investmentbanking.dealpipeline.user.dto.UserResponseDTO;
import com.investmentbanking.dealpipeline.user.model.User;
import com.investmentbanking.dealpipeline.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /* ---------------- AUTH HELPERS ---------------- */

    private boolean isAdmin() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private String currentUsername() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    /* ---------------- ADMIN ONLY ---------------- */

    @Override
    public UserResponseDTO createUser(UserCreateRequestDTO dto) {

        if (!isAdmin()) {
            throw new AccessDeniedException("Only ADMIN can create users");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .active(true)
                .createdAt(Instant.now())
                .build();

        return toResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        if (!isAdmin()) {
            throw new AccessDeniedException("Only ADMIN can view users");
        }

        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void updateUserStatus(String userId, boolean active) {

        if (!isAdmin()) {
            throw new AccessDeniedException("Only ADMIN can update users");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(active);
        userRepository.save(user);
    }

    /* ---------------- USER / ADMIN ---------------- */

    @Override
    public UserResponseDTO getCurrentUser() {

        User user = userRepository.findByUsername(currentUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return toResponse(user);
    }

    /* ---------------- MAPPER ---------------- */

    private UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
