package com.lukianchykov.usersdatabaseapplication.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Filter parameters for user search")
public class UserFilter {
    
    @Schema(description = "Filter by user ID")
    private String id;
    
    @Schema(description = "Filter by username")
    private String username;
    
    @Schema(description = "Filter by name")
    private String name;
    
    @Schema(description = "Filter by surname")
    private String surname;

    public boolean hasFilters() {
        return id != null && !id.isEmpty()
            || username != null && !username.isEmpty()
            || name != null && !name.isEmpty()
            || surname != null && !surname.isEmpty();
    }

    public boolean isEmpty() {
        return !hasFilters();
    }
}