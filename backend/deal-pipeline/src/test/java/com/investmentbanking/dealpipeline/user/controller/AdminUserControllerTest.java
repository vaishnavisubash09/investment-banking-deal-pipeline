package com.investmentbanking.dealpipeline.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investmentbanking.dealpipeline.security.CustomUserDetailsService;
import com.investmentbanking.dealpipeline.security.JwtAuthenticationFilter;
import com.investmentbanking.dealpipeline.security.JwtUtil;
import com.investmentbanking.dealpipeline.user.dto.UpdateUserStatusRequest;
import com.investmentbanking.dealpipeline.user.dto.UserCreateRequestDTO;
import com.investmentbanking.dealpipeline.user.dto.UserResponseDTO;
import com.investmentbanking.dealpipeline.user.model.Role;
import com.investmentbanking.dealpipeline.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminUserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(AdminUserControllerTest.MockConfig.class)
class AdminUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {

        @Bean
        UserService userService() {
            return Mockito.mock(UserService.class);
        }

        // ---- SECURITY (DUMMY) ----
        @Bean JwtUtil jwtUtil() { return Mockito.mock(JwtUtil.class); }
        @Bean JwtAuthenticationFilter jwtAuthenticationFilter() { return Mockito.mock(JwtAuthenticationFilter.class); }
        @Bean CustomUserDetailsService customUserDetailsService() { return Mockito.mock(CustomUserDetailsService.class); }
    }

    private UserResponseDTO sampleUser() {
        return UserResponseDTO.builder()
                .id("1")
                .username("admin")
                .email("admin@test.com")
                .role(Role.ADMIN)
                .active(true)
                .createdAt(Instant.now())
                .build();
    }

    // ---------- CREATE ----------
    @Test
    void createUser() throws Exception {
        when(userService.createUser(Mockito.any()))
                .thenReturn(sampleUser());

        mockMvc.perform(post("/api/admin/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new UserCreateRequestDTO())))
                .andExpect(status().isOk());
    }

    // ---------- GET ----------
    @Test
    void getUsers() throws Exception {
        when(userService.getAllUsers())
                .thenReturn(List.of(sampleUser()));

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk());
    }

    // ---------- UPDATE STATUS ----------
    @Test
    void updateStatus() throws Exception {
        mockMvc.perform(put("/api/admin/users/1/status")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new UpdateUserStatusRequest(false))))
                .andExpect(status().isOk());
    }
}
