package com.skypro.recommender.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.skypro.recommender.model.Views;
import com.skypro.recommender.model.dto.ProductDTO;
import com.skypro.recommender.service.DynamicRulesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rule")
public class DynamicRulesController {

    private final DynamicRulesService dynamicRulesService;

    public DynamicRulesController(DynamicRulesService dynamicRulesService) {
        this.dynamicRulesService = dynamicRulesService;
    }

    @PostMapping
    @JsonView(Views.Response.class)
    public ResponseEntity<ProductDTO> addProduct(
            @RequestBody @JsonView(Views.Request.class) ProductDTO product) throws JsonProcessingException {
        dynamicRulesService.createProduct(product);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("{product_id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("product_id") String productId) {
        dynamicRulesService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(dynamicRulesService.getAllRules());
    }
}
