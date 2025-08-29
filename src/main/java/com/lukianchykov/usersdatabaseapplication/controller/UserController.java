package com.lukianchykov.usersdatabaseapplication.controller;

import java.util.List;

import com.lukianchykov.usersdatabaseapplication.domain.User;
import com.lukianchykov.usersdatabaseapplication.domain.UserFilter;
import com.lukianchykov.usersdatabaseapplication.service.UserAggregationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User aggregation API")
public class UserController {

    private final UserAggregationService userAggregationService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve users from all configured databases")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
                     content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<User>> getUsers(
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String surname) {

        UserFilter filter = new UserFilter();
        filter.setId(id);
        filter.setUsername(username);
        filter.setName(name);
        filter.setSurname(surname);

        List<User> users = userAggregationService.getAllUsers(filter);
        return ResponseEntity.ok(users);
    }
}