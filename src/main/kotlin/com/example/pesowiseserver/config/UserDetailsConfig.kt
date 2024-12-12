//package com.example.pesowiseserver.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.provisioning.InMemoryUserDetailsManager
//import org.springframework.security.crypto.password.PasswordEncoder
//
//@Configuration
//class UserDetailsConfig(
//    private val passwordEncoder: PasswordEncoder
//) {
//    @Bean
//    fun inMemoryUserDetailsManager(): InMemoryUserDetailsManager {
//        val user: UserDetails = User.builder()
//            .username("user")
//            .password(passwordEncoder.encode("password"))
//            .roles("USER")
//            .build()
//
//        val admin: UserDetails = User.builder()
//            .username("admin")
//            .password(passwordEncoder.encode("admin"))
//            .roles("ADMIN")
//            .build()
//
//        return InMemoryUserDetailsManager(user, admin)
//    }
//}