package com.example.pesowiseserver.data.repository

import com.example.pesowiseserver.data.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UsersRepository: JpaRepository<Users, String> {

    fun findByUserId(userId: String): Optional<Users?>

    fun findByEmailOrUserName(email: String, userName: String): Optional<Users?>

    fun findByEmail(email: String): Optional<Users?>

    fun findByUserName(userName: String): Optional<Users?>

}