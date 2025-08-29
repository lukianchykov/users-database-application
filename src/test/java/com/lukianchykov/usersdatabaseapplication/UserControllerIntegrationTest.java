package com.lukianchykov.usersdatabaseapplication;

import java.util.List;

import com.lukianchykov.usersdatabaseapplication.domain.User;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    static MySQLContainer<?> mysql1 = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("userdb1")
        .withUsername("testuser")
        .withPassword("testpass")
        .withInitScript("db/init-db1.sql");

    @Container
    static MySQLContainer<?> mysql2 = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("userdb2")
        .withUsername("testuser")
        .withPassword("testpass")
        .withInitScript("db/init-db2.sql");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("data-sources[0].url", mysql1::getJdbcUrl);
        registry.add("data-sources[0].name", () -> "data-base-1");
        registry.add("data-sources[0].strategy", () -> "mysql");
        registry.add("data-sources[0].table", () -> "users");
        registry.add("data-sources[0].user", () -> "testuser");
        registry.add("data-sources[0].password", () -> "testpass");
        registry.add("data-sources[0].mapping.id", () -> "id");
        registry.add("data-sources[0].mapping.username", () -> "username");
        registry.add("data-sources[0].mapping.name", () -> "name");
        registry.add("data-sources[0].mapping.surname", () -> "surname");

        registry.add("data-sources[1].url", mysql2::getJdbcUrl);
        registry.add("data-sources[1].name", () -> "data-base-2");
        registry.add("data-sources[1].strategy", () -> "mysql");
        registry.add("data-sources[1].table", () -> "user_table");
        registry.add("data-sources[1].user", () -> "testuser");
        registry.add("data-sources[1].password", () -> "testpass");
        registry.add("data-sources[1].mapping.id", () -> "ldap_login");
        registry.add("data-sources[1].mapping.username", () -> "ldap_login");
        registry.add("data-sources[1].mapping.name", () -> "name");
        registry.add("data-sources[1].mapping.surname", () -> "surname");
    }

    @Test
    void shouldReturnAllUsersWithFilter() {
        ResponseEntity<List<User>> response = restTemplate.exchange(
            "/users?username=user-1",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        response.getBody().forEach(user ->
            assertThat(user.getUsername()).contains("user-1")
        );
    }

    @Test
    void shouldReturnUsersWithoutFilter() {
        ResponseEntity<List<User>> response = restTemplate.exchange(
            "/users",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSizeGreaterThan(0);

        assertThat(response.getBody().stream()
            .anyMatch(u -> u.getUsername().equals("user-1"))).isTrue();
        assertThat(response.getBody().stream()
            .anyMatch(u -> u.getUsername().equals("admin_user"))).isTrue();
    }
}
