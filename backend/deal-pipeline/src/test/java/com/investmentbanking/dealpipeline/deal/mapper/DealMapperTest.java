package com.investmentbanking.dealpipeline.deal.mapper;

import com.investmentbanking.dealpipeline.deal.dto.DealResponseDTO;
import com.investmentbanking.dealpipeline.deal.model.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealMapperTest {

    private final DealMapper mapper = new DealMapper();

    private Deal createDeal(List<DealNote> notes) {
        return Deal.builder()
                .id("1")
                .clientName("ACME")
                .dealType(DealType.IPO)
                .sector("Tech")
                .summary("Test")
                .currentStage(DealStage.PROSPECT)
                .dealValue(5000L)
                .notes(notes)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    void admin_shouldSeeDealValue() {
        Deal deal = createDeal(null);

        DealResponseDTO dto = mapper.toResponseDTO(deal, true);

        assertEquals(5000L, dto.getDealValue());
    }

    @Test
    void user_shouldNotSeeDealValue() {
        Deal deal = createDeal(null);

        DealResponseDTO dto = mapper.toResponseDTO(deal, false);

        assertNull(dto.getDealValue());
    }

    @Test
    void nullNotes_shouldReturnEmptyList() {
        Deal deal = createDeal(null);

        DealResponseDTO dto = mapper.toResponseDTO(deal, true);

        assertNotNull(dto.getNotes());
        assertTrue(dto.getNotes().isEmpty());
    }

    @Test
    void emptyNotes_shouldReturnEmptyList() {
        Deal deal = createDeal(List.of());

        DealResponseDTO dto = mapper.toResponseDTO(deal, true);

        assertTrue(dto.getNotes().isEmpty());
    }

    @Test
    void notes_shouldBeMapped() {
        DealNote note = DealNote.builder()
                .userId("u1")
                .note("hello")
                .timestamp(Instant.now())
                .build();

        Deal deal = createDeal(List.of(note));

        DealResponseDTO dto = mapper.toResponseDTO(deal, true);

        assertEquals(1, dto.getNotes().size());
        assertEquals("u1", dto.getNotes().get(0).getUserId());
        assertEquals("hello", dto.getNotes().get(0).getNote());
    }
}
