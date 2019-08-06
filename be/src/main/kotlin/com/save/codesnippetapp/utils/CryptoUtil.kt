package com.save.codesnippetapp.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.Charset
import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


@Component
class CryptoUtil(@Value("\${crypto.key}") val cryptoKey: String) {

    private val charset: Charset = Charsets.UTF_8

    fun encrypt(secureCode: String): String {
        var encryptedSecureCode = ""

        try {
            // Create key and cipher
            val aesKey: Key = SecretKeySpec(cryptoKey.toByteArray(), "AES")
            val cipher: Cipher = Cipher.getInstance("AES")
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey)
            val encrypted: ByteArray = cipher.doFinal(secureCode.toByteArray(charset))
            println("encryptedKey: $encrypted")

            // use base64 encoding to covert as text-readable format
            encryptedSecureCode = Base64.getEncoder().encodeToString(encrypted)
        }
        catch (e: Exception) {
            println(e)
        }
        return encryptedSecureCode
    }

    fun decrypt(token: String?): String? {
        var result: String? = null

        println("token $token")
        if (token != null) {
            try {
                val stringToByteArray: ByteArray = Base64.getDecoder().decode(token)
                val cipher: Cipher = Cipher.getInstance("AES")
                val aesKey: Key = SecretKeySpec(cryptoKey.toByteArray(charset), "AES")
                cipher.init(Cipher.DECRYPT_MODE, aesKey)

                result = String(cipher.doFinal(stringToByteArray))
            } catch (e: Exception) {
                println(e)
            }
        }

        return result
    }
}
