package com.online.education.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resolvedPath = uploadDir.replace("\\", "/");
        if (!resolvedPath.endsWith("/")) {
            resolvedPath += "/";
        }

        System.out.println("Serving static files from: file:" + resolvedPath); // 👈 Add this

        registry.addResourceHandler("/Uploads/**")
                .addResourceLocations("file:" + resolvedPath);
    }

}
