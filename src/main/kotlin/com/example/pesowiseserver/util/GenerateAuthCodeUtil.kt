package com.example.pesowiseserver.util

import com.example.pesowiseserver.data.entity.EmailAuthCode
import com.example.pesowiseserver.data.repository.EmailAuthCodeRepository
import org.springframework.stereotype.Component
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Component
class GenerateAuthCodeUtil(
    private val emailAuthCodeRepository: EmailAuthCodeRepository,
) {

    fun getAuthCode(userId: String): String{
        val now = Date()
        val save = EmailAuthCode()
        save.userId = userId
        save.referenceId = generateReferenceId()
        save.code = generateCode()
        save.isUsed = false
        save.dateExpiry = Date(now.time + TimeUnit.MINUTES.toMillis(5))
        save.dateIssued = now
        emailAuthCodeRepository.save(save)
        return EncryptionUtil.decryptPassword(save.code)
    }

    fun saveAuthCode(userId: String): String{
        val now = Date()
        val save = EmailAuthCode()
        save.userId = userId
        save.referenceId = generateReferenceId()
        save.code = generateCode()
        save.isUsed = false
        save.dateExpiry = Date(now.time + TimeUnit.MINUTES.toMillis(5))
        save.dateIssued = now
        val saved = emailAuthCodeRepository.save(save)
        return saved.authId
    }

    private fun generateCode(): String {
        return EncryptionUtil.encryptPassword(Random.nextInt(100000, 1000000).toString())
    }

    private fun generateReferenceId(): String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..6)
            .map { allowedChars.random() }
            .joinToString("")
    }
}