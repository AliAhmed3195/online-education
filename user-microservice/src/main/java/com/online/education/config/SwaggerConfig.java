package com.online.education.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class SwaggerConfig {

//    @Bean
//    GroupedOpenApi publicApi(){
//        return GroupedOpenApi.builder()
//                .group("public")
//                .pathsToMatch("/public/**")
//                .build();
//    }

    @Bean
    public GroupedOpenApi userMicroserviceApi() {
        return GroupedOpenApi.builder()
                .group("user-microservice")
                .pathsToMatch("/user-microservice/**")
                .pathsToExclude("/auth-server/**")  // Exclude auth server endpoints
                .build();
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("API")
                        .version("v1")
                        .description("API Documentation"))
                .servers(List.of(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:3030")  // Local server
                                .description("Local Server")));
    }
}
