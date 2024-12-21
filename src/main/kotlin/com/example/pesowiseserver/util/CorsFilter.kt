package com.example.pesowiseserver.util
import org.springframework.stereotype.Component
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.FilterConfig
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletResponse

@Component
class CorsFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpResponse = response as HttpServletResponse
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000")
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
        chain.doFilter(request, response)
    }

    override fun init(filterConfig: FilterConfig?) {}

    override fun destroy() {}
}
