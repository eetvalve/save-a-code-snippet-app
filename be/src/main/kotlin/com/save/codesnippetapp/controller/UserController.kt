package com.save.codesnippetapp.controller

import com.save.codesnippetapp.model.User
import com.save.codesnippetapp.repository.UserRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping




@RestController
@RequestMapping("/api")
class UserController (private val userRepository: UserRepository) {

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
