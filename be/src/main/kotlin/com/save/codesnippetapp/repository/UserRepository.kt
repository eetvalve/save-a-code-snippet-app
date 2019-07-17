package com.save.codesnippetapp.repository

import com.save.codesnippetapp.model.Title
import com.save.codesnippetapp.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByUserName(name: String): User?
}
