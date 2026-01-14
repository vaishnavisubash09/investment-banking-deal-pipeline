package com.investmentbanking.dealpipeline.deal.service;

import com.investmentbanking.dealpipeline.dashboard.dto.DashboardSummaryDTO;
import com.investmentbanking.dealpipeline.deal.dto.DealCreateRequestDTO;
import com.investmentbanking.dealpipeline.deal.dto.DealResponseDTO;
import com.investmentbanking.dealpipeline.deal.dto.DealUpdateRequestDTO;
import com.investmentbanking.dealpipeline.deal.model.DealStage;

import java.util.List;

public interface DealService {

    /* ---------------- DEALS ---------------- */

    DealResponseDTO createDeal(DealCreateRequestDTO dto);

    List<DealResponseDTO> getAllDeals();

    DealResponseDTO getDealById(String id);

    DealResponseDTO updateDeal(String id, DealUpdateRequestDTO dto);

    DealResponseDTO updateStage(String id, DealStage stage);

    DealResponseDTO updateDealValue(String id, Long value);

    DealResponseDTO addNote(String id, String note);

    void deleteDeal(String id);

    /* ---------------- DASHBOARD ---------------- */

    DashboardSummaryDTO getUserDashboard();

    DashboardSummaryDTO getAdminDashboard();
}
