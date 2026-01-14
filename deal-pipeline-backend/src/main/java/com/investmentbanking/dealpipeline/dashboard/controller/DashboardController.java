package com.investmentbanking.dealpipeline.dashboard.controller;

import com.investmentbanking.dealpipeline.dashboard.dto.DashboardSummaryDTO;
import com.investmentbanking.dealpipeline.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DealService dealService;

    @GetMapping("/summary")
    public DashboardSummaryDTO summary() {

        boolean isAdmin =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return isAdmin
                ? dealService.getAdminDashboard()
                : dealService.getUserDashboard();
    }
}
