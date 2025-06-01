package com.skypro.recommender.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.skypro.recommender.model.Views;
import com.skypro.recommender.model.Recommendation;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.service.DynamicRulesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы непосредственно с динамическими правилами: их сохранение, просмотр и удаление
 */
@RestController
@RequestMapping("/rule")
public class DynamicRulesController {

    private final DynamicRulesService dynamicRulesService;
    private final RecommendationInfoRepository recommendationInfoRepository;

    public DynamicRulesController(DynamicRulesService dynamicRulesService, RecommendationInfoRepository recommendationInfoRepository) {
        this.dynamicRulesService = dynamicRulesService;
        this.recommendationInfoRepository = recommendationInfoRepository;
    }

    @PostMapping
    @JsonView(Views.Response.class)
    public ResponseEntity<Recommendation> addProduct(
            @RequestBody @JsonView(Views.Request.class) Recommendation product) throws JsonProcessingException {
        dynamicRulesService.createProduct(product);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("{product_id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("product_id") String productId) {
        dynamicRulesService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Recommendation>> getAllProducts() {
        return ResponseEntity.ok(dynamicRulesService.getAllRules());
    }
}
