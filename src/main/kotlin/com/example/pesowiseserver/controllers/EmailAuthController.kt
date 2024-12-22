package com.example.pesowiseserver.controllers

import com.example.pesowiseserver.services.EmailAuthCodeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @PostMapping("/link")
    fun verifyEmailLink(
        @RequestBody dto: VerifyEmailLinkDto
    ): ResponseEntity<Map<String, String>>{
        return emailAuthCodeService.verifyLink(dto.authId, dto.token)
    }
}

data class VerifyEmailLinkDto(
    val authId: String,
    val token: String
)

data class VerifyEmailDto(
    val authId: String,
    val code: String
)