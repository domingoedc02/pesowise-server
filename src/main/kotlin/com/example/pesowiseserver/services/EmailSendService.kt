package com.example.pesowiseserver.services

import jakarta.mail.MessagingException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class EmailSendService(
    private val mailSender: JavaMailSender,
    private val templateEngine: TemplateEngine
) {

    @Throws(MessagingException::class)
    fun sendEmail(to: String, subject: String, authCode: String, name: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        val context = Context().apply {
            setVariable("name", name)
            setVariable("authCode", authCode)
        }

        // Process the template
        val html = templateEngine.process("email-template", context)

        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(html, true) // Enable HTML

        mailSender.send(message)
    }

}