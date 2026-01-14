package com.investmentbanking.dealpipeline.deal.dto;

import com.investmentbanking.dealpipeline.deal.model.DealStage;
import com.investmentbanking.dealpipeline.deal.model.DealType;
import lombok.Builder;
import lombok.Data;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DealResponseDTO {

    private String id;
    private String clientName;
    private String dealType;
    private String sector;
    private String summary;
    private String currentStage;
    private Long dealValue;          // nullable for USER
    private List<DealNoteDTO> notes; // ✅ FIXED
    private Instant createdAt;
    private Instant updatedAt;
}
