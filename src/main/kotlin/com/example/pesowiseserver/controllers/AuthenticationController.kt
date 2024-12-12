package com.example.pesowiseserver.controllers

import com.example.pesowiseserver.data.repository.AccountsRepository
import com.example.pesowiseserver.services.AuthenticationService
import com.example.pesowiseserver.util.EncryptionUtil
import com.example.pesowiseserver.util.JwtUtil
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
    private val accountsRepository: AccountsRepository
) {

    @PostMapping("/register")
    fun registerAccount(@RequestBody dto: RegisterAccountDto): ResponseStatus{
        return authenticationService.registerNewAccount(dto)
    }

    @PostMapping("/login")
    fun loginAccount(@RequestBody dto: LoginDto, response: HttpServletResponse): ResponseEntity<Map<String, String>> {
        val userAccount = accountsRepository.findByEmailOrUserName(dto.username, dto.username)

        val objectMapper = jacksonObjectMapper()
        val jsonNode = objectMapper.readTree(userAccount?.password?.let { EncryptionUtil.decryptPassword(it) })

        val password = jsonNode["password"].asText()

        if ((dto.username == userAccount?.userName && dto.password == password || dto.username == userAccount?.email && dto.password == password)) {  // Mock validation
            val accessToken = JwtUtil.generateAccessToken(userAccount.userName)
            val refreshToken = JwtUtil.generateRefreshToken(userAccount.userName)
            response.addHeader("Authorization", "Bearer $accessToken")
            return ResponseEntity.ok(mapOf(
                "accessToken" to accessToken,
                "refreshToken" to refreshToken
            ))
        }

        return ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))
    }

    @PostMapping("/token/refresh")
    fun refresh(@RequestParam refreshToken: String): ResponseEntity<Map<String, String>> {
        try {
            val username = JwtUtil.extractUsername(refreshToken)
            if (username != null && !JwtUtil.isTokenExpired(refreshToken)) {
                val newAccessToken = JwtUtil.generateAccessToken(username)
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
    val email: String
)

data class ResponseStatus(
    val status: HttpStatus,
    val message: String,
    val data: RegisterAccountDto?
)

data class LoginDto(
    val username: String,
    val password: String
)