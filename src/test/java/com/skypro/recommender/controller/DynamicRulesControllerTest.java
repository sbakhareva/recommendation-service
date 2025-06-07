package com.skypro.recommender.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.recommender.model.Recommendation;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.service.DynamicRulesService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DynamicRulesController.class)
class DynamicRulesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private DynamicRulesService dynamicRulesService;
    @MockitoBean
    private RecommendationInfoRepository recommendationInfoRepository;

    @Test
    void createRuleTest() throws Exception {
        UUID recommendationId = UUID.randomUUID();
        Rule rule = new Rule("правило", List.of("аргументы"), true, recommendationId);
        Recommendation recommendation =
                new Recommendation("рекомендация", recommendationId, "описание", List.of(rule));

        when(dynamicRulesService.createRuleByRecommendationId(any(Rule.class), any(UUID.class))).thenReturn(recommendation);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/rule/" + recommendationId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rule)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recommendationId.toString()))
                .andExpect(jsonPath("$.name").value(recommendation.getName()))
                .andExpect(jsonPath("$.rules[0].query").value(rule.getQuery()));
    }

    @Test
    void createRecommendationWithRulesTest() throws Exception {
        UUID recommendationId = UUID.randomUUID();
        Rule rule = new Rule("правило", List.of("аргументы"), true, recommendationId);
        Recommendation recommendation = new Recommendation();
        recommendation.setId(recommendationId);
        recommendation.setName("name");
        recommendation.setDescription("description");
        recommendation.setRules(List.of(rule));

        when(recommendationInfoRepository.getRecommendationWithRules(any(UUID.class))).thenReturn(recommendation);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/rule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recommendation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recommendation.getId().toString()))
                .andExpect(jsonPath("$.name").value(recommendation.getName()))
                .andExpect(jsonPath("$.rules[0].query").value(rule.getQuery()));
    }

    @Test
    void getRecommendationWithRulesTest() throws Exception {
        UUID recommendationId = UUID.randomUUID();
        Rule rule = new Rule("правило", List.of("аргументы"), true, recommendationId);
        Recommendation recommendation = new Recommendation();
        recommendation.setId(recommendationId);
        recommendation.setName("name");
        recommendation.setDescription("description");
        recommendation.setRules(List.of(rule));

        when(dynamicRulesService.getRecommendationWithRules(any(UUID.class))).thenReturn(recommendation);

        mockMvc.perform(get("/rule/" + recommendationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recommendation.getId().toString()))
                .andExpect(jsonPath("$.name").value(recommendation.getName()))
                .andExpect(jsonPath("$.description").value(recommendation.getDescription()))
                .andExpect(jsonPath("$.rules[0].query").value(rule.getQuery()));
    }

    @Test
    void deleteRuleTest() throws Exception {
        UUID recommendationId = UUID.randomUUID();
        Rule rule = new Rule("правило", List.of("аргументы"), true, recommendationId);
        rule.setId(UUID.randomUUID());

        mockMvc.perform(delete("/rule/" + rule.getId()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    void getAllRulesTest() throws Exception {
        UUID recommendationId = UUID.randomUUID();
        Rule rule = new Rule("правило", List.of("аргументы"), true, recommendationId);
        Rule rule1 = new Rule("query", List.of("args"), false, recommendationId);

        when(dynamicRulesService.getAllRules()).thenReturn(List.of(rule, rule1));

        System.out.println(dynamicRulesService.getAllRules());

        mockMvc.perform(get("/rule"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].query").value(rule.getQuery()))
                .andExpect(jsonPath("$[1].query").value(rule1.getQuery()));
    }
}