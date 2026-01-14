package com.investmentbanking.dealpipeline.deal.service;

import com.investmentbanking.dealpipeline.dashboard.dto.DashboardSummaryDTO;
import com.investmentbanking.dealpipeline.deal.dto.*;
import com.investmentbanking.dealpipeline.deal.mapper.DealMapper;
import com.investmentbanking.dealpipeline.deal.model.Deal;
import com.investmentbanking.dealpipeline.deal.model.DealNote;
import com.investmentbanking.dealpipeline.deal.model.DealStage;
import com.investmentbanking.dealpipeline.deal.repository.DealRepository;
import com.investmentbanking.dealpipeline.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;
    private final UserRepository userRepository;

    /* ================= AUTH HELPERS ================= */

    private boolean isAdmin() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private String currentUser() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    /* ================= DEAL COMMANDS ================= */

    @Override
    public DealResponseDTO createDeal(DealCreateRequestDTO dto) {

        Deal deal = Deal.builder()
                .clientName(dto.getClientName())
                .dealType(dto.getDealType())
                .sector(dto.getSector())
                .summary(dto.getSummary())
                .dealValue(isAdmin() ? dto.getDealValue() : null)
                .currentStage(DealStage.PROSPECT)
                .createdBy(currentUser())
                .assignedTo(dto.getAssignedTo())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return dealMapper.toResponseDTO(
                dealRepository.save(deal),
                isAdmin()
        );
    }

    @Override
    public List<DealResponseDTO> getAllDeals() {
        boolean admin = isAdmin();
        return dealRepository.findAll()
                .stream()
                .map(deal -> dealMapper.toResponseDTO(deal, admin))
                .toList();
    }

    @Override
    public DealResponseDTO getDealById(String id) {
        Deal deal = getDeal(id);
        return dealMapper.toResponseDTO(deal, isAdmin());
    }

    @Override
    public DealResponseDTO updateDeal(String id, DealUpdateRequestDTO dto) {
        Deal deal = getDeal(id);

        deal.setDealType(dto.getDealType());
        deal.setSector(dto.getSector());
        deal.setSummary(dto.getSummary());
        deal.setUpdatedAt(Instant.now());

        return dealMapper.toResponseDTO(
                dealRepository.save(deal),
                isAdmin()
        );
    }

    @Override
    public DealResponseDTO updateStage(String id, DealStage stage) {
        Deal deal = getDeal(id);
        deal.setCurrentStage(stage);
        deal.setUpdatedAt(Instant.now());

        return dealMapper.toResponseDTO(
                dealRepository.save(deal),
                isAdmin()
        );
    }

    @Override
    public DealResponseDTO updateDealValue(String id, Long value) {
        if (!isAdmin()) {
            throw new AccessDeniedException("Only ADMIN can update deal value");
        }

        Deal deal = getDeal(id);
        deal.setDealValue(value);
        deal.setUpdatedAt(Instant.now());

        return dealMapper.toResponseDTO(
                dealRepository.save(deal),
                true
        );
    }

    @Override
    public DealResponseDTO addNote(String id, String note) {
        Deal deal = getDeal(id);

        if (deal.getNotes() == null) {
            deal.setNotes(new ArrayList<>());
        }

        deal.getNotes().add(
                DealNote.builder()
                        .userId(currentUser())
                        .note(note)
                        .timestamp(Instant.now())
                        .build()
        );

        return dealMapper.toResponseDTO(
                dealRepository.save(deal),
                isAdmin()
        );
    }

    @Override
    public void deleteDeal(String id) {
        if (!isAdmin()) {
            throw new AccessDeniedException("Only ADMIN can delete deals");
        }
        dealRepository.deleteById(id);
    }

    /* ================= DASHBOARD ================= */

    @Override
    public DashboardSummaryDTO getUserDashboard() {
        long total = dealRepository.count();
        long closed = dealRepository.countByCurrentStage(DealStage.CLOSED);
        long active = total - closed;

        return new DashboardSummaryDTO(total, active, closed, null);
    }

    @Override
    public DashboardSummaryDTO getAdminDashboard() {
        if (!isAdmin()) {
            throw new AccessDeniedException("Only ADMIN can view admin dashboard");
        }

        long total = dealRepository.count();
        long closed = dealRepository.countByCurrentStage(DealStage.CLOSED);
        long active = total - closed;
        long users = userRepository.count();

        return new DashboardSummaryDTO(total, active, closed, users);
    }

    /* ================= INTERNAL ================= */

    private Deal getDeal(String id) {
        return dealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deal not found"));
    }
}
