package com.investmentbanking.dealpipeline.deal.dto;

import com.investmentbanking.dealpipeline.deal.model.DealType;
import lombok.Data;

@Data
public class DealUpdateRequestDTO {
    private DealType dealType;
    private String sector;
    private String summary;
}
