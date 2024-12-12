package com.example.pesowiseserver.services

import com.example.pesowiseserver.controllers.RegisterAccountDto
import com.example.pesowiseserver.controllers.ResponseStatus
import com.example.pesowiseserver.data.entity.Accounts
import com.example.pesowiseserver.data.repository.AccountsRepository
import com.example.pesowiseserver.util.GenerateAuthCodeUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val accountsRepository: AccountsRepository,
    private val emailSendService: EmailSendService,
    private val generateAuthCodeUtil: GenerateAuthCodeUtil
) {

    fun registerNewAccount(dto: RegisterAccountDto): ResponseStatus{

        val accEmail = accountsRepository.findByEmail(dto.email)
        val accUserName = accountsRepository.findByUserName(dto.userName)

        if (accEmail != null) throw RuntimeException("This email is already registered. Pleas enter other email.")
        if (accUserName != null) throw RuntimeException("This username is already registered. Pleas enter other username.")

        val newAccount = Accounts(
            null,
            dto.firstName,
            dto.lastName,
            dto.userName,
            dto.email,
            null
        )
        val saveAcc = accountsRepository.save(newAccount)
        val code = generateAuthCodeUtil.getAuthCode(saveAcc.id!!.toString())
        val subject = "Verification Code for PesoWise"
        emailSendService.sendEmail(dto.email, subject, code, dto.firstName)
        return ResponseStatus(HttpStatus.OK, "Successfully Registered", dto)
    }
}
