package com.skypro.recommender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skypro.recommender.model.dto.ProductDTO;
import com.skypro.recommender.repository.RulesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicRulesService {

    private final RulesRepository rulesRepository;

    public DynamicRulesService(RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    public void createProduct(ProductDTO product) throws JsonProcessingException {
        rulesRepository.createProduct(product);
    }

    public void deleteProductById(String productId) {

        rulesRepository.deleteProductById(productId);
    }

    public List<ProductDTO> getAllRules() {
        return rulesRepository.getAllProducts();
    }


}
