package com.example.pesowiseserver.controllers

import com.example.pesowiseserver.data.entity.Accounts
import com.example.pesowiseserver.services.AccountsService
import com.example.pesowiseserver.util.EncryptionUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounts")
class AccountsController(
    private val accountsService: AccountsService,
) {

    @Value("\${secret.baseUrl}")
    private val baseUrl = ""

    @GetMapping
    fun getAllAccounts(): List<Accounts> {
        return accountsService.getAllAccounts()
    }

    @PostMapping
    fun saveAccount(@RequestBody account: Accounts): Accounts = accountsService.saveAccount(account)

    @GetMapping("/{username}")
    fun getAccountByUsernameOrEmail(@PathVariable username: String): Accounts? = accountsService.getUserByEmailOrUserName(username, username)

    @DeleteMapping("/{id}")
    fun deleteAccount(@PathVariable id: String) = accountsService.deleteAccount(id)

    @PutMapping("/{id}")
    fun addPassword(@PathVariable id: String, @RequestBody password: String): Accounts{
        return accountsService.updatePassword(id, password)
    }
}