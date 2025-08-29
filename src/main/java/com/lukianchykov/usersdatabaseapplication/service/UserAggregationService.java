package com.lukianchykov.usersdatabaseapplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.lukianchykov.usersdatabaseapplication.domain.User;
import com.lukianchykov.usersdatabaseapplication.domain.UserFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAggregationService {

    private final DatabaseQueryService databaseQueryService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public List<User> getAllUsers(UserFilter filter) {
        if (filter == null) {
            filter = new UserFilter();
        }

        UserFilter finalFilter = filter;

        List<CompletableFuture<List<User>>> futures = databaseQueryService.getDataSourceNames()
            .stream()
            .map(dataSourceName -> CompletableFuture.supplyAsync(() -> {
                try {
                    return databaseQueryService.queryDatabase(dataSourceName, finalFilter);
                } catch (Exception e) {
                    log.error("Error querying database {}: {}", dataSourceName, e.getMessage(), e);
                    return new ArrayList<User>();
                }
            }, executorService))
            .toList();

        return futures.stream()
            .map(CompletableFuture::join)
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }
}