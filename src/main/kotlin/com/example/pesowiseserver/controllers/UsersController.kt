package com.example.pesowiseserver.controllers

import com.example.pesowiseserver.data.entity.Accounts
import com.example.pesowiseserver.services.UsersService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UsersController(
    private val usersService: UsersService,
) {

    @Value("\${secret.baseUrl}")
    private val baseUrl = ""

//    @GetMapping
//    fun getAllAccounts(): List<Accounts> {
//        return usersService.getAllAccounts()
//    }
//
//    @PostMapping
//    fun saveAccount(@RequestBody account: Accounts): Accounts = usersService.saveAccount(account)
//
//    @GetMapping("/{username}")
//    fun getAccountByUsernameOrEmail(@PathVariable username: String): Accounts? = usersService.getUserByEmailOrUserName(username, username)

    @DeleteMapping("/{id}")
    fun deleteAccount(@PathVariable id: String) = usersService.deleteAccount(id)

    @PutMapping("/password")
    @CrossOrigin(origins = ["http://localhost:3000"])
    fun addPassword(@RequestBody password: String): ResponseEntity<String>{
        return usersService.updatePassword(password)
    }

    @GetMapping
    fun test(): String{
       return "TEST"
    }
}