package com.skypro.recommender.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.recommender.configuration.CacheConfig;
import lombok.*;
import org.springframework.boot.info.BuildProperties;
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
    private final BuildProperties buildProperties;

    public ManagementController(CacheConfig cacheConfig, BuildProperties buildProperties) {
        this.cacheConfig = cacheConfig;
        this.buildProperties = buildProperties;
    }

    @GetMapping("/info")
    public ServiceInfo getManagementInfo() {
        return new ServiceInfo(buildProperties.getName(), buildProperties.getVersion());
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
