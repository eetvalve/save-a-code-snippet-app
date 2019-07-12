package com.save.codesnippetapp.repository

import com.save.codesnippetapp.model.Snippet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface SnippetRepository : JpaRepository<Snippet, Int> {
    fun getAllByTitleTitleId(titleId: Int): List<Snippet>
}