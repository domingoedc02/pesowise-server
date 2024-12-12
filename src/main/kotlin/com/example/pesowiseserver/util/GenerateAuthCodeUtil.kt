package com.example.pesowiseserver.util

import com.example.pesowiseserver.data.entity.EmailAuthCode
import com.example.pesowiseserver.data.repository.EmailAuthCodeRepository
import com.example.pesowiseserver.util.enum.AuthTypeEnum
import org.springframework.stereotype.Component
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Component
class GenerateAuthCodeUtil(
    private val emailAuthCodeRepository: EmailAuthCodeRepository
) {

    fun getAuthCode(accountId: String): String{
        val authCode = generateCode()
        val refId = generateReferenceId()
        val now = Date()
        val expiration = Date(now.time + TimeUnit.MINUTES.toMillis(5))
        emailAuthCodeRepository.save(EmailAuthCode(null, accountId, refId, authCode, AuthTypeEnum.REGISTER, false, now, expiration))
        return EncryptionUtil.decryptPassword(authCode)
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