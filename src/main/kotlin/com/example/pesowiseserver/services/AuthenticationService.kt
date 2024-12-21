package com.example.pesowiseserver.services

import com.example.pesowiseserver.controllers.LoginDto
import com.example.pesowiseserver.controllers.RegisterAccountDto
import com.example.pesowiseserver.data.entity.Users
import com.example.pesowiseserver.data.repository.UsersRepository
import com.example.pesowiseserver.util.EncryptionUtil
import com.example.pesowiseserver.util.GenerateAuthCodeUtil
import com.example.pesowiseserver.util.JwtUtil
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val usersRepository: UsersRepository,
    private val emailSendService: EmailSendService,
    private val generateAuthCodeUtil: GenerateAuthCodeUtil,
    private val jwtUtil: JwtUtil
) {

    fun login(dto: LoginDto): ResponseEntity<Map<String, String>>{
        val userAccount = usersRepository.findByEmailOrUserName(dto.username, dto.username)

        if (userAccount.isEmpty) return ResponseEntity.status(404).body(mapOf("not_found" to "Users not found"))

        if (!userAccount.get().isVerified) return ResponseEntity.status(403).body(mapOf("message" to "Your account is not yet verified"))

        val objectMapper = jacksonObjectMapper()
        val jsonNode = objectMapper.readTree(userAccount.get().password?.let { EncryptionUtil.decryptPassword(it) })

        val password = jsonNode["password"].asText()

        if ((dto.username == userAccount.get().userName && dto.password == password || dto.username == userAccount.get().email && dto.password == password)) {  // Mock validation
            val accessToken = jwtUtil.generateAccessToken(userAccount.get().userName, userAccount.get().role)
            val refreshToken = jwtUtil.generateRefreshToken(userAccount.get().userName, userAccount.get().role)
            return ResponseEntity.ok(mapOf(
                "accessToken" to accessToken,
                "refreshToken" to refreshToken
            ))
        } else {
            return ResponseEntity.status(400).body(mapOf("bad_request" to "Wrong credentials, Please try again"))
        }
    }

    fun registerNewAccount(dto: RegisterAccountDto): ResponseEntity<String>{

        val accEmail = usersRepository.findByEmail(dto.email)
        val accUserName = usersRepository.findByUserName(dto.userName)

        if (accUserName.isPresent) return ResponseEntity.status(400).body("The username is already exist, Please try again")
        if (accEmail.isPresent) return ResponseEntity.status(400).body("The email is already exist, Please try again")

        val newAccount = Users()
        newAccount.firstName = dto.firstName
        newAccount.lastName = dto.lastName
        newAccount.userName = dto.userName
        newAccount.email = dto.email
        newAccount.password = dto.password
        val saveAcc = usersRepository.save(newAccount)
//        val code = generateAuthCodeUtil.getAuthCode(saveAcc.userId)
        val authId = generateAuthCodeUtil.saveAuthCode(saveAcc.userId)
        val subject = "Verification Code"
        val accessToken = jwtUtil.generateAccessToken(saveAcc.userName, saveAcc.role)
        emailSendService.sendEmail(dto.email, subject, dto.firstName, authId, accessToken)



        return ResponseEntity.status(200).body(accessToken)
    }
}
