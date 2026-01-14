package com.investmentbanking.dealpipeline.deal.service;

import com.investmentbanking.dealpipeline.dashboard.dto.DashboardSummaryDTO;
import com.investmentbanking.dealpipeline.deal.dto.*;
import com.investmentbanking.dealpipeline.deal.mapper.DealMapper;
import com.investmentbanking.dealpipeline.deal.model.*;
import com.investmentbanking.dealpipeline.deal.repository.DealRepository;
import com.investmentbanking.dealpipeline.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock DealRepository dealRepository;
    @Mock DealMapper dealMapper;
    @Mock UserRepository userRepository;

    @InjectMocks DealServiceImpl service;

    Deal deal;

    @BeforeEach
    void setup() {
        deal = Deal.builder()
                .id("1")
                .dealValue(100L)
                .currentStage(DealStage.PROSPECT)
                .build();
    }

    void admin() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "admin", null,
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                )
        );
    }

    void user() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "user", null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );
    }

    @AfterEach
    void clear() {
        SecurityContextHolder.clearContext();
    }

    // ---------------- CREATE ----------------

    @Test
    void createDeal_admin_setsValue() {
        admin();
        DealCreateRequestDTO dto = new DealCreateRequestDTO();
        dto.setDealValue(999L);

        when(dealRepository.save(any())).thenReturn(deal);
        when(dealMapper.toResponseDTO(any(), eq(true))).thenReturn(new DealResponseDTO());

        service.createDeal(dto);

        verify(dealRepository).save(any());
    }

    @Test
    void createDeal_user_hidesValue() {
        user();
        when(dealRepository.save(any())).thenReturn(deal);
        when(dealMapper.toResponseDTO(any(), eq(false))).thenReturn(new DealResponseDTO());

        service.createDeal(new DealCreateRequestDTO());

        verify(dealMapper).toResponseDTO(any(), eq(false));
    }

    // ---------------- UPDATE ----------------

    @Test
    void updateDeal_success() {
        user();
        when(dealRepository.findById("1")).thenReturn(Optional.of(deal));
        when(dealRepository.save(any())).thenReturn(deal);
        when(dealMapper.toResponseDTO(any(), anyBoolean())).thenReturn(new DealResponseDTO());

        service.updateDeal("1", new DealUpdateRequestDTO());

        verify(dealRepository).save(deal);
    }

    @Test
    void updateDeal_notFound() {
        user();
        when(dealRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.updateDeal("1", new DealUpdateRequestDTO()));
    }

    // ---------------- STAGE ----------------

    @Test
    void updateStage_success() {
        user();
        when(dealRepository.findById("1")).thenReturn(Optional.of(deal));
        when(dealRepository.save(any())).thenReturn(deal);
        when(dealMapper.toResponseDTO(any(), anyBoolean())).thenReturn(new DealResponseDTO());

        service.updateStage("1", DealStage.CLOSED);

        assertEquals(DealStage.CLOSED, deal.getCurrentStage());
    }

    // ---------------- VALUE ----------------

    @Test
    void adminCanUpdateValue() {
        admin();
        when(dealRepository.findById("1")).thenReturn(Optional.of(deal));
        when(dealRepository.save(any())).thenReturn(deal);
        when(dealMapper.toResponseDTO(any(), eq(true))).thenReturn(new DealResponseDTO());

        service.updateDealValue("1", 500L);

        assertEquals(500L, deal.getDealValue());
    }

    @Test
    void userCannotUpdateValue() {
        user();
        assertThrows(AccessDeniedException.class,
                () -> service.updateDealValue("1", 100L));
    }

    // ---------------- DELETE ----------------

    @Test
    void adminCanDelete() {
        admin();
        service.deleteDeal("1");
        verify(dealRepository).deleteById("1");
    }

    @Test
    void userCannotDelete() {
        user();
        assertThrows(AccessDeniedException.class,
                () -> service.deleteDeal("1"));
    }

    // ---------------- DASHBOARD ----------------

    @Test
    void userDashboard() {
        user();
        when(dealRepository.count()).thenReturn(10L);
        when(dealRepository.countByCurrentStage(DealStage.CLOSED)).thenReturn(4L);

        DashboardSummaryDTO dto = service.getUserDashboard();

        assertEquals(6, dto.getActiveDeals());
        assertNull(dto.getTotalUsers());
    }

    @Test
    void adminDashboard() {
        admin();
        when(dealRepository.count()).thenReturn(10L);
        when(dealRepository.countByCurrentStage(DealStage.CLOSED)).thenReturn(4L);
        when(userRepository.count()).thenReturn(7L);

        DashboardSummaryDTO dto = service.getAdminDashboard();

        assertEquals(7L, dto.getTotalUsers());
    }

    // ---------------- NOTES ----------------

    @Test
    void addNote_shouldCreateListWhenNull() {
        user();
        deal.setNotes(null);

        when(dealRepository.findById("1")).thenReturn(Optional.of(deal));
        when(dealRepository.save(any())).thenReturn(deal);
        when(dealMapper.toResponseDTO(any(), anyBoolean()))
                .thenReturn(new DealResponseDTO());

        service.addNote("1", "Test");

        assertEquals(1, deal.getNotes().size());
    }

    // ---------------- READ ----------------

    @Test
    void getAllDeals_asUser() {
        user();
        when(dealRepository.findAll()).thenReturn(List.of(deal));
        when(dealMapper.toResponseDTO(any(), eq(false)))
                .thenReturn(new DealResponseDTO());

        List<DealResponseDTO> list = service.getAllDeals();

        assertEquals(1, list.size());
    }

    @Test
    void getDealById_asUser() {
        user();
        when(dealRepository.findById("1")).thenReturn(Optional.of(deal));
        when(dealMapper.toResponseDTO(any(), eq(false)))
                .thenReturn(new DealResponseDTO());

        DealResponseDTO dto = service.getDealById("1");

        assertNotNull(dto);
    }

    @Test
    void getDealById_notFound() {
        user();
        when(dealRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.getDealById("1"));
    }
}
