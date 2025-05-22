package com.skypro.recommender.service.impl;

import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.repository.RecommendationsRepository;
import com.skypro.recommender.service.RecommendationRuleSet;
import com.skypro.recommender.utils.FileReaderUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleLoanServiceImpl implements RecommendationRuleSet {

    private final RecommendationsRepository recommendationsRepository;
    private final FileReaderUtil fileReaderUtil;

    public SimpleLoanServiceImpl(RecommendationsRepository recommendationsRepository, FileReaderUtil fileReaderUtil) {
        this.recommendationsRepository = recommendationsRepository;
        this.fileReaderUtil = fileReaderUtil;
    }

    @Override
    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        String filePath = "text/SimpleLoanText.txt";
        if ((recommendationsRepository.checkIfUserHasTransactionTypeCredit(userId) == 0)
                && (recommendationsRepository.getTotalDebitDeposit(userId)
                > recommendationsRepository.getTotalDebitWithdraw(userId))
                && (recommendationsRepository.getTotalDebitWithdraw(userId) > 100000)) {
            return Optional.of(new RecommendationDTO("Simple Loan",
                    UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                    fileReaderUtil.readText(filePath)));
        }
        return Optional.empty();
    }
}
