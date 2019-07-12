package com.save.codesnippetapp.repository

import com.save.codesnippetapp.model.Title
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface TitleRepository : JpaRepository<Title, Int> {
    // ignoreCase means that query is case insensitive
    fun getFirstByTitleIgnoreCase(title: String) : Title?
    fun findByTitleContaining(title: String): List<Title>
}