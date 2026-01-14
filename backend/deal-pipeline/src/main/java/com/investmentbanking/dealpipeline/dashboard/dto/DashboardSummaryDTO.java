package com.investmentbanking.dealpipeline.dashboard.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DashboardSummaryDTO {

    private long totalDeals;
    private long activeDeals;
    private long closedDeals;

    // ADMIN only — null for USER
    private Long totalUsers;
}
