package com.lukianchykov.usersdatabaseapplication.strategy;

import com.lukianchykov.usersdatabaseapplication.domain.UserFilter;

public interface DatabaseStrategy {

    String buildQuery(String table, java.util.Map<String, String> mapping, UserFilter filter);

    String getDriverClassName();
}