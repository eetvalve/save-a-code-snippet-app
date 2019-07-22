package com.save.codesnippetapp.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class UserService(@Value("\${secure.code}") val secureCode: String) {

     fun validateSecureCode(code: String): Boolean {
         return secureCode == code
     }
}