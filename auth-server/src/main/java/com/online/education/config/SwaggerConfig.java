package com.online.education.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;


public class SwaggerConfig {

//    @Bean
//    public GroupedOpenApi publicApi(){
//        return GroupedOpenApi.builder()
//                .group("public")
//                .pathsToMatch("/public/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi authApi() {
//        return GroupedOpenApi.builder()
//                .group("auth-server")
//                .pathsToMatch("/auth-server/**")
//                .build();
//    }
//
//    @Bean
//    public OpenAPI customOpenApi() {
//        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))  // Link security requirement to all endpoints
//                .components(new Components()
//                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("bearer")
//                                .bearerFormat("JWT")))  // Define the bearer authentication scheme
//                .info(new Info()
//                        .title("API Documentation")
//                        .version("v1")
//                        .description("API documentation with bearer authentication"));
//    }
}