package com.example.pesowiseserver.services

import com.example.pesowiseserver.data.repository.AccountsRepository
import com.example.pesowiseserver.data.repository.EmailAuthCodeRepository
import com.example.pesowiseserver.util.EncryptionUtil
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.Date

@Service
class EmailAuthCodeService(
    private val accountsRepository: AccountsRepository,
    private val emailAuthCodeRepository: EmailAuthCodeRepository
) {

    fun verifyEmail(authId: String, code: String): EmailAuthResponse{
        val now = Date()
        val auth = emailAuthCodeRepository.findById(authId).orElseThrow{
            RuntimeException("Authentication Not Found")
        }

        val account = accountsRepository.findById(auth.accountId).orElseThrow{
            RuntimeException("Account Not Found")
        }

        if (auth.isUsed) throw RuntimeException("This code is already used")

        if (now > auth.dateExpiry) throw RuntimeException("This code is already expired")

        if (account.isVerified) throw RuntimeException("Your account is already verified")

        val authCode = EncryptionUtil.decryptPassword(auth.code)

        if (authCode == code){
            account.isVerified = true
            accountsRepository.save(account)

            auth.isUsed = true
            emailAuthCodeRepository.save(auth)

            return EmailAuthResponse(HttpStatus.OK, "You're account is successfully verified.")
        } else {
            return EmailAuthResponse(HttpStatus.BAD_REQUEST, "Authentication Failed, Please retry again.")
        }
    }
}

data class EmailAuthResponse(
    val status: HttpStatus,
    val message: String?
)