package com.lukianchykov.usersdatabaseapplication.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.lukianchykov.usersdatabaseapplication.strategy.DatabaseStrategy;
import com.lukianchykov.usersdatabaseapplication.strategy.MySQLStrategy;
import com.lukianchykov.usersdatabaseapplication.strategy.OracleStrategy;
import com.lukianchykov.usersdatabaseapplication.strategy.PostgresStrategy;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
    
    private final DataSourceProperties dataSourceProperties;
    
    @Bean
    public Map<String, DataSource> dataSources() {
        Map<String, DataSource> dataSources = new HashMap<>();
        
        for (DataSourceProperties.DataSourceConfig config : dataSourceProperties.getDataSources()) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(config.getUrl());
            hikariConfig.setUsername(config.getUser());
            hikariConfig.setPassword(config.getPassword());
            hikariConfig.setPoolName(config.getName() + "-pool");
            hikariConfig.setMaximumPoolSize(5);
            hikariConfig.setMinimumIdle(2);
            
            dataSources.put(config.getName(), new HikariDataSource(hikariConfig));
            log.info("Configured datasource: {}", config.getName());
        }
        
        return dataSources;
    }
    
    @Bean
    public Map<String, DatabaseStrategy> databaseStrategies() {
        Map<String, DatabaseStrategy> strategies = new HashMap<>();
        strategies.put("postgres", new PostgresStrategy());
        strategies.put("mysql", new MySQLStrategy());
        strategies.put("oracle", new OracleStrategy());
        return strategies;
    }
}