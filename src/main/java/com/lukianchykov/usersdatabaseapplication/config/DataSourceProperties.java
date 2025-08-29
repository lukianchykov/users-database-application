package com.lukianchykov.usersdatabaseapplication.config;

import java.util.List;
import java.util.Map;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "")
public class DataSourceProperties {
    private List<DataSourceConfig> dataSources;

    @Data
    public static class DataSourceConfig {
        private String name;
        private String strategy;
        private String url;
        private String table;
        private String user;
        private String password;
        private Map<String, String> mapping;
    }
}