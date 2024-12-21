package com.example.pesowiseserver.services

import com.example.pesowiseserver.data.repository.UsersRepository
import com.example.pesowiseserver.util.AuthProviderUtil
import com.example.pesowiseserver.util.EncryptionUtil
import com.example.pesowiseserver.util.JwtUtil
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UsersService(
    private val usersRepository: UsersRepository,
    private val authProviderUtil: AuthProviderUtil
) {

    fun deleteAccount(id: String) = usersRepository.deleteById(id)

    fun updatePassword(newPassword: String): ResponseEntity<String>{
        val user = usersRepository.findByUserName(authProviderUtil.getUsername())
        if (user.isPresent){
            user.get().password = EncryptionUtil.encryptPassword(newPassword)
            usersRepository.save(user.get())
            return ResponseEntity.status(200).body("Update Password Successful")
        } else {
            return ResponseEntity.status(400).body("Something error, we cannot find your account to update your password")
        }
    }


}