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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.*


@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) : WebMvcConfigurer {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { cors -> cors.configurationSource(corsConfigurationSource()) } // Disable the default CORS policy
            .csrf { it.disable() } // Disable CSRF (usually for stateless apps)
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/auth/**", "/api/auth/verify/email/link").permitAll() // Allow auth routes
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN", "PRO", "TESTER")
                    .requestMatchers("/api/pro/**").hasAnyRole("ADMIN", "PRO", "TESTER")
                    .anyRequest().authenticated() // Require authentication for other routes
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java) // Add JWT filter
        return http.build()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("https://auth.gopesowise.com") // Allow specific origin
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
//        val corsConfiguration = CorsConfiguration()
//        corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
//        corsConfiguration.allowedHeaders = listOf(
//            "content-type", "accessToken", "x-xsrf-token",
//            "Access-Control-Allow-Origin", "x-os-version", "x-app-version",
//            "x-os-type", "Authorization"
//        )
//        corsConfiguration.allowCredentials = true
//        corsConfiguration.setAllowedOriginPatterns(listOf("*"))
//
//        val corsSource: UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
//        corsSource.registerCorsConfiguration("/api/**", corsConfiguration)
//        corsSource.registerCorsConfiguration("/incoming/**", corsConfiguration)
//        corsSource.registerCorsConfiguration("/images/**", corsConfiguration)
//        corsSource.registerCorsConfiguration("/api/auth/verify/email/link", corsConfiguration)
//
//        return corsSource
    }
}
