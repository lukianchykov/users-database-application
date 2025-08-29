package com.lukianchykov.usersdatabaseapplication.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import com.lukianchykov.usersdatabaseapplication.config.DataSourceProperties;
import com.lukianchykov.usersdatabaseapplication.domain.User;
import com.lukianchykov.usersdatabaseapplication.domain.UserFilter;
import com.lukianchykov.usersdatabaseapplication.strategy.DatabaseStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseQueryService {

    private final Map<String, DataSource> dataSources;

    private final Map<String, DatabaseStrategy> databaseStrategies;

    private final DataSourceProperties dataSourceProperties;

    public Set<String> getDataSourceNames() {
        return dataSources.keySet();
    }

    public List<User> queryDatabase(String dataSourceName, UserFilter filter) {
        DataSource dataSource = dataSources.get(dataSourceName);
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource not found: " + dataSourceName);
        }

        DataSourceProperties.DataSourceConfig config = dataSourceProperties.getDataSources()
            .stream()
            .filter(ds -> ds.getName().equals(dataSourceName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Config not found: " + dataSourceName));

        DatabaseStrategy strategy = databaseStrategies.get(config.getStrategy());
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy not found: " + config.getStrategy());
        }

        if (filter == null) {
            filter = new UserFilter();
        }

        String sql = strategy.buildQuery(config.getTable(), config.getMapping(), filter);

        log.info("Executing query on {}: {}", dataSourceName, sql);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query(sql, (rs, rowNum) -> User.builder()
            .id(rs.getString("id"))
            .username(rs.getString("username"))
            .name(rs.getString("name"))
            .surname(rs.getString("surname"))
            .build());
    }
}