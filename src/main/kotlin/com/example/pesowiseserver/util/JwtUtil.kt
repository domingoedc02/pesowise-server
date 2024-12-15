package com.example.pesowiseserver.util

import com.example.pesowiseserver.util.enum.RoleEnum
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import java.util.concurrent.TimeUnit

@Component
class JwtUtil(
    @Value("\${secret.jwtKey}") private val secretKey: String = ""
) {

    private val SECRET_KEY = Keys.hmacShaKeyFor(secretKey.toByteArray())
    private val ACCESS_TOKEN_EXPIRATION = 30L // in minutes
    private val REFRESH_TOKEN_EXPIRATION = 7L // in days

    fun generateAccessToken(username: String, role: RoleEnum): String {
        val claims = mapOf("username" to username, "role" to role)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(ACCESS_TOKEN_EXPIRATION)))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact()
    }

    fun generateRefreshToken(username: String, role: RoleEnum): String {
        val claims = mapOf("username" to username, "role" to role)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(REFRESH_TOKEN_EXPIRATION)))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact()
    }

    fun extractUsername(token: String): String? {
        val claims = Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .body

        val username = claims["username"] as String

        return username
    }

    fun extractRole(token: String): RoleEnum? {
        val claims = Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .body

        // Assuming the role is stored in the "role" claim
        val roleFromToken = claims["role"] as String?

        return roleFromToken?.let {
            try {
                RoleEnum.valueOf(it)
            } catch (e: IllegalArgumentException) {
                null // Ignore invalid roles
            }
        }
    }

    fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun validateToken(token: String): Boolean {
        return try {
            getClaims(token) != null
        } catch (e: Exception) {
            false
        }
    }

    private fun extractExpiration(token: String): Date {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .body
            .expiration
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .body
    }
}