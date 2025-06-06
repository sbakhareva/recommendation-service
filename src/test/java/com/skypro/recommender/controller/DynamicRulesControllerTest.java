package com.skypro.recommender.controller;

import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.service.DynamicRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(DynamicRulesController.class)
class DynamicRulesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private DynamicRulesService dynamicRulesService;
    @MockitoBean
    private RecommendationInfoRepository recommendationInfoRepository;

}