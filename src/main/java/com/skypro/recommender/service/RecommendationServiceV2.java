package com.skypro.recommender.service;

import com.skypro.recommender.model.Rule;
import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.repository.DynamicRulesRepository;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.repository.RecommendationsRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Вторая версия сервиса, работающая с динамическими правилами, сохраненными в базе данных
 */
@Service
public class RecommendationServiceV2 {

    private final DynamicRulesRepository dynamicRulesRepository;
    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationInfoRepository recommendationInfoRepository;
    private Map<RecommendationDTO, List<Rule>> recommendationRules;

    public RecommendationServiceV2(DynamicRulesRepository dynamicRulesRepository,
                                   RecommendationsRepository recommendationsRepository,
                                   RecommendationInfoRepository recommendationInfoRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationInfoRepository = recommendationInfoRepository;
        initRules();
    }

    private void initRules() {
        recommendationRules = new HashMap<>();

        recommendationRules.put(recommendationInfoRepository.getRecommendation(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f")),
                dynamicRulesRepository.getRules(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f")
                ));

        recommendationRules.put(recommendationInfoRepository.getRecommendation(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925")),
                dynamicRulesRepository.getRules(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925")));

        recommendationRules.put(recommendationInfoRepository.getRecommendation(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a")),
                dynamicRulesRepository.getRules(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a")));
    }

    public List<RecommendationDTO> getRecommendations(UUID userId) {
        List<RecommendationDTO> suitableRecommendations = new ArrayList<>();

        for (Map.Entry<RecommendationDTO, List<Rule>> entry : recommendationRules.entrySet()) {
            RecommendationDTO recommendation = entry.getKey();
            List<Rule> rules = entry.getValue();

            boolean allPassed = true;
            for (Rule rule : rules) {
                boolean result = checkRules(userId, rule);
                if (!result) {
                    allPassed = false;
                    break;
                }
            }
            if (allPassed) {
                suitableRecommendations.add(recommendation);
            }
        }
        return suitableRecommendations;
    }

    private boolean checkRules(UUID userId, Rule rule) {

        return switch (rule.getQuery()) {
            case "USER_OF" -> recommendationsRepository.checkIfUserUseProduct(userId, rule.getArguments().get(0));
            case "ACTIVE_USER_OF" -> recommendationsRepository.checkIfUserIsActiveUserOfProduct(userId,
                    rule.getArguments().get(0));
            case "TRANSACTION_SUM_COMPARE" -> recommendationsRepository.transactionSumCompare(userId,
                    rule.getArguments().get(0),
                    rule.getArguments().get(1),
                    rule.getArguments().get(2),
                    Integer.parseInt(rule.getArguments().get(3)));
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" ->
                    recommendationsRepository.transactionSumCompareDepositWithdraw(userId,
                            rule.getArguments().get(0),
                            rule.getArguments().get(1));
            default -> false;
        };
    }
}
