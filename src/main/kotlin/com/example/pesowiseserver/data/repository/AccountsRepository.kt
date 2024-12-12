package com.example.pesowiseserver.data.repository

import com.example.pesowiseserver.data.entity.Accounts
import org.springframework.data.jpa.repository.JpaRepository

interface AccountsRepository: JpaRepository<Accounts, String> {

    fun findByEmailOrUserName(email: String, userName: String): Accounts?

    fun findByEmail(email: String): Accounts?

    fun findByUserName(userName: String): Accounts?

}