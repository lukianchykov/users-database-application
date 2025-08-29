package com.lukianchykov.usersdatabaseapplication.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lukianchykov.usersdatabaseapplication.domain.UserFilter;


public class MySQLStrategy implements DatabaseStrategy {
    
    @Override
    public String buildQuery(String table, Map<String, String> mapping, UserFilter filter) {
        StringBuilder query = new StringBuilder("SELECT ");
        query.append(mapping.get("id")).append(" as id, ");
        query.append(mapping.get("username")).append(" as username, ");
        query.append(mapping.get("name")).append(" as name, ");
        query.append(mapping.get("surname")).append(" as surname ");
        query.append("FROM ").append(table);
        
        if (filter != null && filter.hasFilters()) {
            List<String> conditions = new ArrayList<>();
            
            if (filter.getId() != null) {
                conditions.add(mapping.get("id") + " = '" + filter.getId() + "'");
            }
            if (filter.getUsername() != null) {
                conditions.add(mapping.get("username") + " LIKE '%" + filter.getUsername() + "%'");
            }
            if (filter.getName() != null) {
                conditions.add(mapping.get("name") + " LIKE '%" + filter.getName() + "%'");
            }
            if (filter.getSurname() != null) {
                conditions.add(mapping.get("surname") + " LIKE '%" + filter.getSurname() + "%'");
            }
            
            if (!conditions.isEmpty()) {
                query.append(" WHERE ").append(String.join(" AND ", conditions));
            }
        }

        return query.toString();
    }
    
    @Override
    public String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }
}