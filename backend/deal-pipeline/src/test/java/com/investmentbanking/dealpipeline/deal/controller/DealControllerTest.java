package com.investmentbanking.dealpipeline.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investmentbanking.dealpipeline.deal.dto.*;
import com.investmentbanking.dealpipeline.deal.model.DealStage;
import com.investmentbanking.dealpipeline.deal.service.DealService;
import com.investmentbanking.dealpipeline.security.CustomUserDetailsService;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DealController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(DealControllerTest.MockConfig.class)
class DealControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DealService dealService;

    @Autowired
    ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {

        // ---- BUSINESS SERVICE ----
        @Bean
        DealService dealService() {
            return Mockito.mock(DealService.class);
        }

        // ---- SECURITY INFRA ----
        @Bean
        JwtUtil jwtUtil() {
            return Mockito.mock(JwtUtil.class);
        }

        @Bean
        CustomUserDetailsService customUserDetailsService() {
            return Mockito.mock(CustomUserDetailsService.class);
        }

        @Bean
        JwtAuthenticationFilter jwtAuthenticationFilter() {
            return Mockito.mock(JwtAuthenticationFilter.class);
        }
    }

    // ---------- CREATE ----------
    @Test
    void createDeal() throws Exception {
        when(dealService.createDeal(Mockito.any()))
                .thenReturn(new DealResponseDTO());

        mockMvc.perform(post("/api/deals")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // ---------- GET ALL ----------
    @Test
    void getAllDeals() throws Exception {
        when(dealService.getAllDeals())
                .thenReturn(List.of(new DealResponseDTO()));

        mockMvc.perform(get("/api/deals"))
                .andExpect(status().isOk());
    }

    // ---------- GET ----------
    @Test
    void getDealById() throws Exception {
        when(dealService.getDealById("1"))
                .thenReturn(new DealResponseDTO());

        mockMvc.perform(get("/api/deals/1"))
                .andExpect(status().isOk());
    }

    // ---------- UPDATE ----------
    @Test
    void updateDeal() throws Exception {
        when(dealService.updateDeal(Mockito.eq("1"), Mockito.any()))
                .thenReturn(new DealResponseDTO());

        mockMvc.perform(put("/api/deals/1")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk());
    }

    // ---------- STAGE ----------
    @Test
    void updateStage() throws Exception {
        when(dealService.updateStage("1", DealStage.CLOSED))
                .thenReturn(new DealResponseDTO());

        mockMvc.perform(patch("/api/deals/1/stage")
                        .contentType("application/json")
                        .content("{\"stage\":\"CLOSED\"}"))
                .andExpect(status().isOk());
    }

    // ---------- VALUE ----------
    @Test
    void updateValue() throws Exception {
        when(dealService.updateDealValue("1", 500L))
                .thenReturn(new DealResponseDTO());

        mockMvc.perform(patch("/api/deals/1/value")
                        .contentType("application/json")
                        .content("{\"dealValue\":500}"))
                .andExpect(status().isOk());
    }

    // ---------- NOTES ----------
    @Test
    void addNote() throws Exception {
        when(dealService.addNote("1", "hello"))
                .thenReturn(new DealResponseDTO());

        mockMvc.perform(post("/api/deals/1/notes")
                        .contentType("application/json")
                        .content("{\"note\":\"hello\"}"))
                .andExpect(status().isOk());
    }

    // ---------- DELETE ----------
    @Test
    void deleteDeal() throws Exception {
        mockMvc.perform(delete("/api/deals/1"))
                .andExpect(status().isOk());
    }
}
