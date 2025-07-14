package com.online.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InventoryMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryMicroserviceApplication.class, args);
    }

}
