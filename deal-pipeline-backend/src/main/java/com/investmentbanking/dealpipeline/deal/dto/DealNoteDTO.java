package com.investmentbanking.dealpipeline.deal.dto;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class DealNoteDTO {
    private String userId;
    private String note;
    private Instant timestamp;
}
