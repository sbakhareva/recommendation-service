package com.skypro.recommender.service;

import com.skypro.recommender.model.Rule;
import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.repository.DynamicRulesRepository;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.repository.RecommendationsRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Вторая версия сервиса рекомендаций, работающая с динамическими правилами, сохраненными в базе данных
 * Сервис возвращает список рекомендаций для пользователя по переданному user_id
 */
@Service
public class RecommendationServiceV2 {

    private final DynamicRulesRepository dynamicRulesRepository;
    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationInfoRepository recommendationInfoRepository;

    public RecommendationServiceV2(DynamicRulesRepository dynamicRulesRepository,
                                   RecommendationsRepository recommendationsRepository,
                                   RecommendationInfoRepository recommendationInfoRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationInfoRepository = recommendationInfoRepository;
    }


    /**
     * Метод, который формирует список рекомендаций для пользователя по переданному user_id
     * @param userId идентификатор пользователя
     * @return список рекомендаций для конкретного пользователя
     */
    public List<RecommendationDTO> getRecommendations(UUID userId) {

        List<RecommendationDTO> recommendations = recommendationInfoRepository.getAllRecommendations();
        List<RecommendationDTO> suitableRecommendations = new ArrayList<>();

        for (RecommendationDTO recommendation : recommendations) {
            if (recommendation.getRules() == null || recommendation.getRules().isEmpty()) {
                suitableRecommendations.add(recommendation);
                continue;
            }

            List<Rule> rules = recommendation.getRules();

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

    /**
     * Метод, проверяющий, подходит ли пользователь под критерии, заданные динамическими правилами в базе данных
     * @param userId идентификатор пользователя
     * @param rule динамическое правило, из которого мы получаем критерии для проверки пользователя
     * @return boolean-результат, прошло ли правило проверку
     */
    private boolean checkRules(UUID userId, Rule rule) {

        List<String> arguments = rule.getArguments();
        boolean result = switch (rule.getQuery()) {
            case "USER_OF" ->
                recommendationsRepository.checkIfUserUsesProduct(userId, arguments.get(0));
            case "ACTIVE_USER_OF" ->
                recommendationsRepository.checkIfUserIsActive(userId,
                        arguments.get(0));
            case "TRANSACTION_SUM_COMPARE" -> recommendationsRepository.transactionSumCompare(userId,
                    arguments.get(0),
                    arguments.get(1),
                    arguments.get(2),
                    Integer.parseInt(arguments.get(3)));
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" ->
                    recommendationsRepository.transactionSumCompareDepositWithdraw(userId,
                            arguments.get(0),
                            arguments.get(1));
            default -> false;
        };
        if (result) {
            dynamicRulesRepository.incrementCounter(rule.getId());
        }
        return rule.getNegate() != result;
    }
}
