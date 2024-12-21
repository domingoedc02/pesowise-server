package com.example.pesowiseserver.controllers

import com.example.pesowiseserver.data.repository.UsersRepository
import com.example.pesowiseserver.services.AuthenticationService
import com.example.pesowiseserver.util.JwtUtil
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
    private val jwtUtil: JwtUtil,
    private val usersRepository: UsersRepository
) {

    @PostMapping("/register")
    fun registerAccount(@RequestBody dto: RegisterAccountDto): ResponseEntity<String>{
        return authenticationService.registerNewAccount(dto)
    }

    @PostMapping("/login")
    fun loginAccount(@RequestBody dto: LoginDto, response: HttpServletResponse): ResponseEntity<Map<String, String>> {
        return authenticationService.login(dto)
    }

    @PostMapping("/token/refresh")
    fun refresh(@RequestParam refreshToken: String): ResponseEntity<Map<String, String>> {
        try {
            val username = jwtUtil.extractUsername(refreshToken)
            if (username != null && jwtUtil.isTokenExpired(refreshToken)) {
                val user = usersRepository.findByUserName(username)
                if (user.isEmpty) return ResponseEntity.status(400).body(mapOf("bad_request" to "Invalid Username"))
                val newAccessToken = jwtUtil.generateAccessToken(username, user.get().role)
                return ResponseEntity.ok(mapOf("accessToken" to newAccessToken))
            } else {
                return ResponseEntity.status(400).body(mapOf("error" to "Invalid or expired refresh token"))
            }
        } catch (e: Exception) {
            return ResponseEntity.status(400).body(mapOf("error" to "Invalid refresh token"))
        }
    }

}

data class RegisterAccountDto(
    val firstName: String,
    val lastName: String,
    val userName: String,
    val email: String,
    val password: String
)

data class LoginDto(
    val username: String,
    val password: String
)