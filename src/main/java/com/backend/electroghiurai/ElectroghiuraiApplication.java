package com.backend.electroghiurai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ElectroghiuraiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectroghiuraiApplication.class, args);
    }

}
