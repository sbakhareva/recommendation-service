package com.skypro.recommender.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.recommender.configuration.CacheConfig;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/management")
public class ManagementController {

    private final CacheConfig cacheConfig;

    public ManagementController(CacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;
    }

    @GetMapping("/info")
    public ServiceInfo getManagementInfo() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("target/classes/build-info.json")));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, ServiceInfo.class);
    }

    @PostMapping("/clear-caches")
    public void clearCaches() {
        cacheConfig.clearAllCaches();
    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
class ServiceInfo {
    String name;
    String version;
}
