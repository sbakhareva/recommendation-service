package com.skypro.recommender.service;

import com.skypro.recommender.service.impl.SimpleLoanServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class RecommendationService {

    private final SimpleLoanServiceImpl simpleLoanService;

    public RecommendationService(SimpleLoanServiceImpl simpleLoanService) {
        this.simpleLoanService = simpleLoanService;
    }
}
