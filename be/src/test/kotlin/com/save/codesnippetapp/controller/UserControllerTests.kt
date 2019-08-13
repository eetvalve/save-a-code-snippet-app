package com.save.codesnippetapp.controller

import com.save.codesnippetapp.model.User
import com.save.codesnippetapp.repository.UserRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // cleanup h2 db after every testcase
class UserControllerTests {

    @Autowired
    lateinit var userRepository: UserRepository


    @Before
    fun addUserToh2Db() {
        val user = User(1, "edu", false)
        userRepository.save(user)
    }


    @Test
    fun existingUserShouldBeFound() {

        val existingUser: User? = userRepository.findByUserName("edu")

        if (existingUser != null) {
            Assert.assertEquals(existingUser.userName, "edu")
        }
    }

    @Test
    fun shouldUpdateUserPrivacyoptionToBeTruthy() {

        val user: User = userRepository.findById(1).get()

        val privateSnippets: Boolean = !user.privateSnippets
        val updatedUser = User(user.userId, user.userName, privateSnippets)

        val userRes: User = userRepository.save(updatedUser)

        Assert.assertEquals(userRes.privateSnippets, true)
    }
}