package com.example.pesowiseserver.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**") // Allow all endpoints
                    .allowedOrigins("http://localhost:3000") // Allow specific origin
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                    .allowedHeaders("*") // Allow all headers
                    .allowCredentials(true) // Allow cookies or authorization headers
            }
        }
    }
}
