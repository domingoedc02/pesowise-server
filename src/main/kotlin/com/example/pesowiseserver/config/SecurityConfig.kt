package com.example.pesowiseserver.config

import com.example.pesowiseserver.config.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) : WebMvcConfigurer {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { it.disable() } // Disable the default CORS policy
            .csrf { it.disable() } // Disable CSRF (usually for stateless apps)
            .authorizeRequests { auth ->
                auth
                    .requestMatchers("/api/auth/**", "/api/auth/verify/email").permitAll() // Allow auth routes
                    .anyRequest().authenticated() // Require authentication for other routes
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java) // Add JWT filter
        return http.build()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    // CORS configuration
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000") // Allow frontend URL
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific methods
            .allowedHeaders("*") // Allow all headers
            .allowCredentials(true) // Allow credentials (cookies, HTTP authentication)
    }
}
