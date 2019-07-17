package com.save.codesnippetapp.repository

import com.save.codesnippetapp.model.Snippet
import com.save.codesnippetapp.model.Title
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface SnippetRepository : JpaRepository<Snippet, Int> {
    fun getAllByTitleTitleId(titleId: Int): List<Snippet>

    @Query(value = "SELECT * FROM snippets s WHERE s.owner = :owner AND s.title_id = :title " +
            "OR s.is_private_snippet = FALSE AND s.title_id = :title ", nativeQuery = true)
    fun findAllTitleSpecificSnippets(@Param("owner") owner: Int, @Param("title") title: Int): List<Snippet>

}
