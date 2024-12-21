package com.example.pesowiseserver.util

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthProviderUtil {

    fun getUsername(): String{
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("No authentication found in the context")
        return authentication.name
    }

    fun getAuthenticationDatails(): Authentication {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("No authentication found in the context")
        return authentication
    }

}