package com.example.pesowiseserver.services

import com.example.pesowiseserver.data.entity.Users
import com.example.pesowiseserver.data.repository.UsersRepository
import com.example.pesowiseserver.data.repository.EmailAuthCodeRepository
import com.example.pesowiseserver.util.EncryptionUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class EmailAuthCodeService(
    private val usersRepository: UsersRepository,
    private val emailAuthCodeRepository: EmailAuthCodeRepository,
) {

    fun verifyEmail(authId: String, code: String): ResponseEntity<String>{
        val now = Date()
        val auth = emailAuthCodeRepository.findByAuthId(authId)

        if (auth.isEmpty) return ResponseEntity.status(404).body("Authentication code not registered")

        val account = usersRepository.findById(auth.get().userId)

        if (account.isEmpty) return ResponseEntity.status(404).body("Your user account is not found")

        if (auth.get().isUsed) return ResponseEntity.status(400).body("This code is already used, Please try to request new code")

        if (now > auth.get().dateExpiry) ResponseEntity.status(400).body("This code is already expired, Add braces to 'if' statement")

        if (account.get().isVerified) ResponseEntity.status(400).body("Your email is already used/verified")

        val authCode = EncryptionUtil.decryptPassword(auth.get().code)

        if (authCode == code){
            account.get().isVerified = true
            usersRepository.save(account.get())

            auth.get().isUsed = true
            emailAuthCodeRepository.save(auth.get())

            return ResponseEntity.status(200).body("Email verification successfully")
        } else {
            return ResponseEntity.status(400).body("Email verification failed")
        }
    }

    fun verifyLink(id: String, token: String): ResponseEntity<Map<String, String>>{
        val auth = emailAuthCodeRepository.findByAuthId(id)

        if (auth.isEmpty || auth.get().isUsed) return ResponseEntity.status(404).body(mapOf("statusText" to "Authentication Failed"))

        val user = usersRepository.findByUserId(auth.get().userId)

        if (user.isEmpty) return ResponseEntity.status(404).body(mapOf("statusText" to "User Not Found"))

        if (user.get().isVerified) return ResponseEntity.status(400).body(mapOf("statusText" to "Your account is already verified"))

        user.get().isVerified = true
        usersRepository.save(user.get())

        auth.get().isUsed = true
        emailAuthCodeRepository.save(auth.get())

        return ResponseEntity.status(200).body(mapOf("statusText" to "Your account is now verified"))
    }
}