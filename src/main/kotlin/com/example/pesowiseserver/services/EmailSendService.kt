package com.example.pesowiseserver.services

import jakarta.mail.MessagingException
import org.springframework.beans.factory.annotation.Value
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
    @Value("\${secret.verificationBaseUrl}")
    private val baseUrl: String = ""

    @Throws(MessagingException::class)
    fun sendEmail(to: String, subject: String, name: String, codeId: String, token: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        val verificationUrl = "${baseUrl}/verification?codeId=${codeId}&token=${token}"
        val context = Context().apply {
            setVariable("name", name)
            setVariable("verificationUrl", verificationUrl)
        }

        // Process the template
        val html = templateEngine.process("email-template", context)

        helper.setTo(to)
        helper.setFrom("GoPESOWISE <verify@gopesowise.com>")
        helper.setSubject(subject)
        helper.setText(html, true) // Enable HTML

        mailSender.send(message)
    }

    @Throws(MessagingException::class)
    fun verificationSuccessEmail(email: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        val context = Context().apply {
            setVariable("email", email)
        }

        // Process the template
        val html = templateEngine.process("email-template", context)

        helper.setTo(email)
        helper.setFrom("GoPESOWISE <verify@gopesowise.com>")
        helper.setSubject("Verification Successful")
        helper.setText(html, true) // Enable HTML

        mailSender.send(message)
    }

}