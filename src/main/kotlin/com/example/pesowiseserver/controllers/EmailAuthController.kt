package com.example.pesowiseserver.controllers

import com.example.pesowiseserver.services.EmailAuthCodeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth/verify/email")
class EmailAuthController(
    private val emailAuthCodeService: EmailAuthCodeService
) {

    @PostMapping
    fun verifyEmail(@RequestBody dto: VerifyEmailDto): ResponseEntity<String>{
        return emailAuthCodeService.verifyEmail(dto.authId, dto.code)
    }
}

data class VerifyEmailDto(
    val authId: String,
    val code: String
)