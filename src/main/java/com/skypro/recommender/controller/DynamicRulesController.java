package com.skypro.recommender.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.skypro.recommender.model.Views;
import com.skypro.recommender.model.Recommendation;
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

    public DynamicRulesController(DynamicRulesService dynamicRulesService) {
        this.dynamicRulesService = dynamicRulesService;
    }

    @PostMapping
    @JsonView(Views.Response.class)
    public ResponseEntity<Recommendation> addRule(
            @RequestBody @JsonView(Views.Request.class) Recommendation recommendation) {

        return ResponseEntity.ok(dynamicRulesService.createRule(recommendation));
    }

    @DeleteMapping("{product_id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("product_id") String ruleId) {
        dynamicRulesService.deleteRule(ruleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Recommendation>> getAllRules() {
        return ResponseEntity.ok(dynamicRulesService.getAllRules());
    }
}
