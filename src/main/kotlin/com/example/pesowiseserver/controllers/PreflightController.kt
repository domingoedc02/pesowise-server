package com.example.pesowiseserver.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class PreflightController {

    @RequestMapping(value = ["/**"], method = [RequestMethod.OPTIONS])
    fun preflight(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}
