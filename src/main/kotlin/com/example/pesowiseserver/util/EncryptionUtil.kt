package com.example.pesowiseserver.util

import org.springframework.beans.factory.annotation.Value
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {

    private const val ALGORITHM = "AES"

    // Static secret key for encryption and decryption (128, 192, or 256 bits)
    @Value("\${secret.key}")
    private const val BASE64_SECRET_KEY = "hQm4Khsf0pjIx8RNrg3LTbrbCsiiW6V78iyiaF1k4wg="

    // Decode the secret key from Base64 string
    private val secretKey: SecretKey by lazy {
        decodeKey(BASE64_SECRET_KEY)
    }

    // Encrypt a password
    fun encryptPassword(password: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(password.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    // Decrypt a password
    fun decryptPassword(encryptedPassword: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword))
        return String(decryptedBytes)
    }

    // Convert a Base64-encoded key string to a SecretKey
    private fun decodeKey(encodedKey: String): SecretKey {
        val decodedKey = Base64.getDecoder().decode(encodedKey)
        return SecretKeySpec(decodedKey, ALGORITHM)
    }

    // Generate and print a new Base64-encoded key (for initial setup)
    fun generateAndPrintKey() {
        val newKey = KeyGenerator.getInstance(ALGORITHM).apply {
            init(256) // AES 256-bit key
        }.generateKey()

        println("Generated Base64 Key: ${Base64.getEncoder().encodeToString(newKey.encoded)}")
    }
}