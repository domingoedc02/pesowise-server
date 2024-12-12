package com.example.pesowiseserver.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import java.util.concurrent.TimeUnit

object JwtUtil {

    private const val SECRET_KEY = "Cbf9iP65ogrWZhBI6Tevdc4XAySdNJaCSBxd2M8XDzk="
    private const val ACCESS_TOKEN_EXPIRATION = 30L // in minutes
    private const val REFRESH_TOKEN_EXPIRATION = 7L // in days

    fun generateAccessToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(ACCESS_TOKEN_EXPIRATION)))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact()
    }

    fun generateRefreshToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(REFRESH_TOKEN_EXPIRATION)))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact()
    }

    fun extractUsername(token: String): String? {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .body
            .expiration
    }
}