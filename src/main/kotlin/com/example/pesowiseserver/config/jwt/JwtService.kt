//package com.example.pesowiseserver.config.jwt
//
//import io.jsonwebtoken.Jwts
//import io.jsonwebtoken.SignatureAlgorithm
//import io.jsonwebtoken.security.Keys
//import java.util.Date
//
//class JwtService {
//    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256) // Generates a secure random key
//
//    fun generateToken(username: String): String {
//        return Jwts.builder()
//            .setSubject(username)
//            .setIssuedAt(Date())
//            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
//            .signWith(secretKey, SignatureAlgorithm.HS256) // Specify the key and algorithm explicitly
//            .compact()
//    }
//
//    fun validateToken(token: String): Boolean {
//        try {
//            Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//            return true
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return false
//        }
//    }
//}
