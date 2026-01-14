package com.investmentbanking.dealpipeline.deal.model;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealNote {

    private String userId;
    private String note;
    private Instant timestamp;

}
