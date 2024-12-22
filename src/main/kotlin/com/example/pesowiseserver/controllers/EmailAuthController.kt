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
@CrossOrigin(origins = ["https://auth.gopesowise.com"])
class EmailAuthController(
    private val emailAuthCodeService: EmailAuthCodeService
) {

    @PostMapping
    fun verifyEmail(@RequestBody dto: VerifyEmailDto): ResponseEntity<String>{
        return emailAuthCodeService.verifyEmail(dto.authId, dto.code)
    }

    @GetMapping("/link")
    fun verifyEmailLink(
        @RequestParam("id") id: String,
        @RequestParam("token") token: String
    ): ResponseEntity<Map<String, String>>{
        return emailAuthCodeService.verifyLink(id, token)
    }
}

data class VerifyEmailDto(
    val authId: String,
    val code: String
)