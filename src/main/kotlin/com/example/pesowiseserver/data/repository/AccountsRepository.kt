package com.example.pesowiseserver.data.repository

import com.example.pesowiseserver.data.entity.Accounts
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AccountsRepository: JpaRepository<Accounts, String> {

    fun findByAccountId(accountId: String): Optional<Accounts?>
}