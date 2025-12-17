package com.hsjh.nowdo.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path profileDir = Paths.get("uploads/profile/").toAbsolutePath();
        registry.addResourceHandler("/api/uploads/profile/**")
            .addResourceLocations(profileDir.toUri().toString());
    }
}
