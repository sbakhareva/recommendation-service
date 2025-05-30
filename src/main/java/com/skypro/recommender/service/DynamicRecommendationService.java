package com.skypro.recommender.service;

import com.skypro.recommender.model.dto.ProductDTO;
import com.skypro.recommender.model.dto.RuleDTO;
import com.skypro.recommender.repository.DynamicRecommendationRepository;
import com.skypro.recommender.repository.RulesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DynamicRecommendationService {

    DynamicRecommendationRepository repository;
    RulesRepository rulesRepository;

    public DynamicRecommendationService(DynamicRecommendationRepository repository,
                                        RulesRepository rulesRepository)

    {
        this.repository = repository;
        this.rulesRepository = rulesRepository;
    }

    public boolean evaluateRule(UUID userId, RuleDTO rule) {

        List<String> queryArgs = rule.getArguments();

        boolean result = switch (rule.getQuery()) {
            case "USER_OF" -> repository.isUserOfProduct(userId, queryArgs.get(0));

            case "ACTIVE_USER_OF" -> repository.isActiveUserOfProduct(userId, queryArgs.get(0));

            case "TRANSACTION_SUM_COMPARE" -> repository.compareTransactionSum(userId, queryArgs);

            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" ->
                    repository.compareTransactionSumDepositWithdraw(userId, queryArgs);

            default -> throw new IllegalStateException("Unexpected value: " + rule.getQuery());
        };
        return rule.getNegate() != result;
    }

    public List<ProductDTO> getRecommendation(UUID userId) {

        List<ProductDTO> products = new ArrayList<>(rulesRepository.getAllProducts());
        List<ProductDTO> recommendations = new ArrayList<>();

        for (ProductDTO product : products) {
            if (product.getRules() == null || product.getRules().isEmpty()) {
                recommendations.add(product);
                continue;
            }

            boolean allRules = true;

            for (RuleDTO rule : product.getRules()) {
                if (!evaluateRule(userId, rule)) {
                    allRules = false;
                    break;
                }
            }
            if (allRules) {
                recommendations.add(product);
            }
        }
        return recommendations;
    }
}
