package com.investmentbanking.dealpipeline.deal.mapper;

import com.investmentbanking.dealpipeline.deal.dto.DealNoteDTO;
import com.investmentbanking.dealpipeline.deal.dto.DealResponseDTO;
import com.investmentbanking.dealpipeline.deal.model.Deal;
import com.investmentbanking.dealpipeline.deal.model.DealNote;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DealMapper {

    public DealResponseDTO toResponseDTO(Deal deal, boolean isAdmin) {

        return DealResponseDTO.builder()
                .id(deal.getId())
                .clientName(deal.getClientName())
                .dealType(deal.getDealType().name())
                .sector(deal.getSector())
                .summary(deal.getSummary())
                .currentStage(deal.getCurrentStage().name())
                .dealValue(isAdmin ? deal.getDealValue() : null)
                .notes(mapNotes(deal.getNotes()))
                .createdAt(deal.getCreatedAt())
                .updatedAt(deal.getUpdatedAt())
                .build();
    }

    private List<DealNoteDTO> mapNotes(List<DealNote> notes) {
        if (notes == null || notes.isEmpty()) {
            return List.of(); // ✅ never return null
        }

        return notes.stream()
                .map(this::mapNote)
                .toList();
    }

    private DealNoteDTO mapNote(DealNote note) {
        return DealNoteDTO.builder()
                .userId(note.getUserId())
                .note(note.getNote())
                .timestamp(note.getTimestamp())
                .build();
    }
}
