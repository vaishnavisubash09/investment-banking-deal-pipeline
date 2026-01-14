package com.investmentbanking.dealpipeline.deal.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "deals")

public class Deal {

    @Id
    private String id;

    private String clientName;

    private DealType dealType;
    private String sector;

    private Long dealValue; // ADMIN only

    private DealStage currentStage;

    private String summary;

    private List<DealNote> notes = new ArrayList<>();

    private String createdBy;
    private String assignedTo;

    private Instant createdAt;
    private Instant updatedAt;
}
