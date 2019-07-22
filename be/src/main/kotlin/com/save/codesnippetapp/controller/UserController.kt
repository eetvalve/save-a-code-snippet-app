package com.save.codesnippetapp.controller

import com.save.codesnippetapp.model.User
import com.save.codesnippetapp.repository.UserRepository
import com.save.codesnippetapp.service.UserService
import com.save.codesnippetapp.utils.CryptoUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@RestController
@RequestMapping("/api")
class UserController (private val userRepository: UserRepository,
                      private val userService: UserService,
                      private val cryptoUtil: CryptoUtil) {

    @PostMapping("/validateSecureCode/{secureCode}")
    fun validateSecureCode(@PathVariable(value = "secureCode") secureCode: String): ResponseEntity<String?> {
        val isValidCode = userService.validateSecureCode(secureCode)
        if (isValidCode) {
            return ResponseEntity(cryptoUtil.encrypt(secureCode), HttpStatus.FOUND)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/initUser/{userName}")
    fun initUser(@PathVariable(value = "userName") userName: String): User {
        var user: User? = userRepository.findByUserName(userName)

        if (user == null) {
           user = userRepository.save(User(0, userName, false))
        }

        return user
    }

    @PutMapping("/togglePrivacy/{id}")
    fun updateNote(@PathVariable(value = "id") userId: Int): User {

        val user: User = userRepository.findById(userId).get()

        val privateSnippets: Boolean = !user.privateSnippets
        val updatedUser = User(user.userId, user.userName, privateSnippets)

        return userRepository.save(updatedUser)
    }
}
