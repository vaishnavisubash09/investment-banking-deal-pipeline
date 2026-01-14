package com.investmentbanking.dealpipeline.user.controller;

import com.investmentbanking.dealpipeline.security.CustomUserDetailsService;
import com.investmentbanking.dealpipeline.security.JwtAuthenticationFilter;
import com.investmentbanking.dealpipeline.security.JwtUtil;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(UserControllerTest.MockConfig.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

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

    @Test
    void me() throws Exception {
        when(userService.getCurrentUser()).thenReturn(
                UserResponseDTO.builder()
                        .id("1")
                        .username("user")
                        .email("user@test.com")
                        .role(Role.USER)
                        .active(true)
                        .createdAt(Instant.now())
                        .build()
        );

        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk());
    }
}
