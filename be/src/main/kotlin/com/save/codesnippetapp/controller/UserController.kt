package com.save.codesnippetapp.controller


import com.google.datastore.v1.client.Datastore
import com.save.codesnippetapp.model.User
import com.save.codesnippetapp.repository.UserRepository
import com.save.codesnippetapp.service.UserService
import com.save.codesnippetapp.utils.CryptoUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin(origins = arrayOf("http://localhost:8080", "https://frontend-dot-create-a-code-snippet.appspot.com", "http://35.228.102.144:8080"))
@RestController
@RequestMapping("/api")
class UserController (private val userRepository: UserRepository,
                      private val userService: UserService,
                      private val cryptoUtil: CryptoUtil) {

    @PostMapping("/validateSecureCode/{secureCode}")
    fun validateSecureCode(@PathVariable(value = "secureCode") secureCode: String): ResponseEntity<String?> {
        val isValidCode = userService.validateSecureCode(secureCode)
        if (isValidCode) {
            return ResponseEntity(cryptoUtil.encrypt(secureCode), HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.FORBIDDEN)
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
    fun togglePrivacy(@PathVariable(value = "id") userId: Int): User {

        val user: User = userRepository.findById(userId).get()

        val privateSnippets: Boolean = !user.privateSnippets
        val updatedUser = User(user.userId, user.userName, privateSnippets)

        return userRepository.save(updatedUser)
    }
}
