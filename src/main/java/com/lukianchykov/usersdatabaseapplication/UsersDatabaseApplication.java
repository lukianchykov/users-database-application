package com.lukianchykov.usersdatabaseapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class UsersDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersDatabaseApplication.class, args);
    }

}
