package com.investmentbanking.dealpipeline.deal.repository;


import com.investmentbanking.dealpipeline.deal.model.Deal;
import com.investmentbanking.dealpipeline.deal.model.DealStage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

//public interface DealRepository extends MongoRepository<Deal, String> {
//
//    List<Deal> findByClientNameContainingIgnoreCase(String clientName);
//
//    List<Deal> findByCurrentStage(DealStage stage);
//
//    List<Deal> findByClientNameContainingIgnoreCaseAndCurrentStage(
//            String clientName,
//            DealStage stage
//    );
//}

public interface DealRepository extends MongoRepository<Deal, String> {

    Page<Deal> findByClientNameContainingIgnoreCase(
            String clientName,
            Pageable pageable
    );

    Page<Deal> findByCurrentStage(
            DealStage stage,
            Pageable pageable
    );

    Page<Deal> findByClientNameContainingIgnoreCaseAndCurrentStage(
            String clientName,
            DealStage stage,
            Pageable pageable
    );

    long countByCurrentStage(DealStage stage);
}


