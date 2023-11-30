package com.eztornado.tornadocorebase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/api/auth/**") // or specify a path pattern for more granular control
                        .allowedOrigins("*") // the front-end server address
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // allowed HTTP methods
                        .allowedHeaders("*") // allowed headers
                        .allowCredentials(false); // if you need credentials

                registry.addMapping("/api/**") // or specify a path pattern for more granular control
                        .allowedOrigins("http://localhost:8080") // the front-end server address
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // allowed HTTP methods
                        .allowedHeaders("*") // allowed headers
                        .allowCredentials(true); // if you need credentials
            }
        };
    }
}