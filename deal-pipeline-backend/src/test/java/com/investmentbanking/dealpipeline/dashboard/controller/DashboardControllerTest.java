package com.investmentbanking.dealpipeline.dashboard.controller;

import com.investmentbanking.dealpipeline.dashboard.dto.DashboardSummaryDTO;
import com.investmentbanking.dealpipeline.deal.service.DealService;
import com.investmentbanking.dealpipeline.security.JwtAuthenticationFilter;
import com.investmentbanking.dealpipeline.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashboardController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(DashboardControllerTest.MockConfig.class)
class DashboardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DealService dealService;

    private void admin() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "admin", null,
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                )
        );
    }

    private void user() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "user", null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );
    }

    @Test
    void adminShouldGetAdminDashboard() throws Exception {
        admin();

        DashboardSummaryDTO dto = DashboardSummaryDTO.builder()
                .totalUsers(5L)
                .build();

        when(dealService.getAdminDashboard()).thenReturn(dto);

        mockMvc.perform(get("/api/dashboard/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUsers").value(5));
    }

    @Test
    void userShouldGetUserDashboard() throws Exception {
        user();

        DashboardSummaryDTO dto = DashboardSummaryDTO.builder()
                .activeDeals(7L)
                .build();

        when(dealService.getUserDashboard()).thenReturn(dto);

        mockMvc.perform(get("/api/dashboard/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeDeals").value(7));
    }

    // ----------------------------------------------------
    // This prevents JWT filter from crashing the context
    // ----------------------------------------------------
    @TestConfiguration
    static class MockConfig {

        @Bean
        DealService dealService() {
            return Mockito.mock(DealService.class);
        }

        @Bean
        JwtUtil jwtUtil() {
            return Mockito.mock(JwtUtil.class);
        }

        @Bean
        UserDetailsService userDetailsService() {
            return Mockito.mock(UserDetailsService.class);
        }

        @Bean
        JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService uds) {
            return new JwtAuthenticationFilter(jwtUtil, uds);
        }
    }
}
