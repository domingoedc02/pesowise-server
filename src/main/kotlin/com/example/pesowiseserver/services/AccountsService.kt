package com.example.pesowiseserver.services

import com.example.pesowiseserver.data.entity.Accounts
import com.example.pesowiseserver.data.repository.AccountsRepository
import com.example.pesowiseserver.util.EncryptionUtil
import org.springframework.stereotype.Service

@Service
class AccountsService(
    private val accountsRepository: AccountsRepository
) {

    fun getAllAccounts(): List<Accounts> = accountsRepository.findAll()

    fun getUserByEmailOrUserName(email: String, userName: String): Accounts? = accountsRepository.findByEmailOrUserName(email, userName)

    fun saveAccount(accounts: Accounts): Accounts = accountsRepository.save(accounts)

    fun deleteAccount(id: String) = accountsRepository.deleteById(id)

    fun updatePassword(id: String, newPassword: String): Accounts{
        val account = accountsRepository.findById(id).orElseThrow{
            RuntimeException("Account not Found")
        }
        account.password = EncryptionUtil.encryptPassword(newPassword)
        return accountsRepository.save(account)
    }


}