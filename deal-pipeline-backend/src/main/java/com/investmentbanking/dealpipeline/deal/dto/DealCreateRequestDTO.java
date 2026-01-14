package com.investmentbanking.dealpipeline.deal.dto;

import com.investmentbanking.dealpipeline.deal.model.DealType;
import lombok.Data;

@Data
public class DealCreateRequestDTO {
    private String clientName;
    private DealType dealType;
    private String sector;
    private String summary;
    private String assignedTo;
    private Long dealValue; // ignored for USER
}
