package com.example.pesowiseserver.data.repository

import com.example.pesowiseserver.data.entity.EmailAuthCode
import org.springframework.data.jpa.repository.JpaRepository

interface EmailAuthCodeRepository: JpaRepository<EmailAuthCode, String> {
    fun findByReferenceId(referenceId: String): EmailAuthCode?

}