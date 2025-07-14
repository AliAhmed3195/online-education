package com.online.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderProcessingMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderProcessingMicroserviceApplication.class, args);
    }

}
