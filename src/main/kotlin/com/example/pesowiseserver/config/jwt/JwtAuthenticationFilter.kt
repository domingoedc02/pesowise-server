package com.example.pesowiseserver.config.jwt
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {

    private val secretKey = "Cbf9iP65ogrWZhBI6Tevdc4XAySdNJaCSBxd2M8XDzk=" // Replace with your actual secret key

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7) // Extract the token part

            try {
                // Parse the JWT token
                val username = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .body
                    .subject

                if (username != null && SecurityContextHolder.getContext().authentication == null) {
                    // Create an Authentication object and set it in the SecurityContext
                    val authentication = UsernamePasswordAuthenticationToken(
                        username, null, emptyList()
                    )
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            } catch (e: Exception) {
                logger.error("Invalid JWT token", e)
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response)
    }
}