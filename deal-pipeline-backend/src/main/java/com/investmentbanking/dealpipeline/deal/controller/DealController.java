package com.investmentbanking.dealpipeline.deal.controller;

import com.investmentbanking.dealpipeline.deal.dto.*;
import com.investmentbanking.dealpipeline.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @PostMapping
    public DealResponseDTO createDeal(@RequestBody DealCreateRequestDTO dto) {
        return dealService.createDeal(dto);
    }

    @GetMapping
    public List<DealResponseDTO> getAllDeals() {
        return dealService.getAllDeals();
    }

    @GetMapping("/{id}")
    public DealResponseDTO getDeal(@PathVariable String id) {
        return dealService.getDealById(id);
    }

    @PutMapping("/{id}")
    public DealResponseDTO updateDeal(
            @PathVariable String id,
            @RequestBody DealUpdateRequestDTO dto
    ) {
        return dealService.updateDeal(id, dto);
    }

    @PatchMapping("/{id}/stage")
    public DealResponseDTO updateStage(
            @PathVariable String id,
            @RequestBody DealStageUpdateDTO dto
    ) {
        return dealService.updateStage(id, dto.getStage());
    }

    @PatchMapping("/{id}/value")
    public DealResponseDTO updateValue(
            @PathVariable String id,
            @RequestBody DealValueUpdateDTO dto
    ) {
        return dealService.updateDealValue(id, dto.getDealValue());
    }

    @PostMapping("/{id}/notes")
    public DealResponseDTO addNote(
            @PathVariable String id,
            @RequestBody DealNoteRequestDTO dto
    ) {
        return dealService.addNote(id, dto.getNote());
    }

    @DeleteMapping("/{id}")
    public void deleteDeal(@PathVariable String id) {
        dealService.deleteDeal(id);
    }
}
