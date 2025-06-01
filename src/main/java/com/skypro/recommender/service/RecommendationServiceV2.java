package com.skypro.recommender.service;

import com.skypro.recommender.model.QueryObject;
import com.skypro.recommender.model.Recommendation;
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
    private Map<Recommendation, List<QueryObject>> recommendationRules;

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

    public List<Recommendation> getRecommendations(UUID userId) {
        List<Recommendation> suitableRecommendations = new ArrayList<>();

        for (Map.Entry<Recommendation, List<QueryObject>> entry : recommendationRules.entrySet()) {
            Recommendation recommendation = entry.getKey();
            List<QueryObject> rules = entry.getValue();

            boolean allPassed = true;
            for (QueryObject rule : rules) {
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

    private boolean checkRules(UUID userId, QueryObject queryObject) {
        boolean result;
        switch (queryObject.getQuery()) {
            case "USER_OF" ->
                    result = recommendationsRepository.checkIfUserUseProduct(userId, queryObject.getArguments().get(0));
            case "ACTIVE_USER_OF" -> result = recommendationsRepository.checkIfUserIsActiveUserOfProduct(userId,
                    queryObject.getArguments().get(0));
            case "TRANSACTION_SUM_COMPARE" -> result = recommendationsRepository.transactionSumCompare(userId,
                    queryObject.getArguments().get(0),
                    queryObject.getArguments().get(1),
                    queryObject.getArguments().get(2),
                    Integer.parseInt(queryObject.getArguments().get(3)));
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" ->
                    result = recommendationsRepository.transactionSumCompareDepositWithdraw(userId,
                            queryObject.getArguments().get(0),
                            queryObject.getArguments().get(1));
            default -> result = false;
        }
        System.out.println("Rule: " + queryObject.getQuery() + ", args: " + queryObject.getArguments() + ", result: ");
        return result;
    }
}
