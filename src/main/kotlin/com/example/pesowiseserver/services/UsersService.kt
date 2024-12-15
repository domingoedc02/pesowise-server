package com.example.pesowiseserver.services

import com.example.pesowiseserver.data.repository.UsersRepository
import com.example.pesowiseserver.util.EncryptionUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsersService(
    private val usersRepository: UsersRepository,

) {

    fun deleteAccount(id: String) = usersRepository.deleteById(id)

    fun updatePassword(id: String, newPassword: String): ResponseEntity<String>{
        val account = usersRepository.findByUserId(id)
        if (account.isPresent){
            account.get().password = EncryptionUtil.encryptPassword(newPassword)
            return ResponseEntity.status(200).body("Update Password Successful")
        } else {
            return ResponseEntity.status(400).body("Something error, we cannot find your account to update your password")
        }
    }


}