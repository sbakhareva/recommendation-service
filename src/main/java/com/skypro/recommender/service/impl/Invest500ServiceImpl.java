package com.skypro.recommender.service.impl;

import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.repository.RecommendationsRepository;
import com.skypro.recommender.service.RecommendationRuleSet;
import com.skypro.recommender.utils.FileReaderUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500ServiceImpl implements RecommendationRuleSet {

    private final RecommendationsRepository recommendationsRepository;
    private final FileReaderUtil fileReaderUtil;

    public Invest500ServiceImpl(RecommendationsRepository recommendationsRepository, FileReaderUtil fileReaderUtil) {
        this.recommendationsRepository = recommendationsRepository;
        this.fileReaderUtil = fileReaderUtil;
    }

    @Override
    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        String filePath = "text/Invest500.txt";
        if ((recommendationsRepository.checkIfUserHasTransactionTypeDebit(userId) == 1)
                && (recommendationsRepository.checkIfUserHasTransactionTypeInvest(userId) == 0)
                && (recommendationsRepository.getTotalSavingDeposit(userId) > 1000)) {
            return Optional.of(new RecommendationDTO("Invest 500",
                    UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                    fileReaderUtil.readText(filePath)));
        }
        return Optional.empty();
    }
}
