package com.lukianchykov.usersdatabaseapplication.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User model")
public class User {

    @Schema(description = "User unique identifier", example = "example-user-id-1")
    private String id;

    @Schema(description = "User login", example = "user-1")
    private String username;

    @Schema(description = "User first name", example = "User")
    private String name;

    @Schema(description = "User last name", example = "Userenko")
    private String surname;
}