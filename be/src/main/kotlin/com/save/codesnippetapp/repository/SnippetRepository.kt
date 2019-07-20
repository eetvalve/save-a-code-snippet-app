package com.save.codesnippetapp.repository

import com.save.codesnippetapp.model.Snippet
import com.save.codesnippetapp.model.Title
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository


@Repository
interface SnippetRepository : JpaRepository<Snippet, Int> {
    @Query(value = "SELECT * FROM snippets s WHERE s.owner = :owner AND s.title_id = :title " +
            "OR s.is_private_snippet = FALSE AND s.title_id = :title ", nativeQuery = true)
    fun findAllTitleSpecificSnippets(@Param("owner") owner: Int, @Param("title") title: Int): List<Snippet>

    fun getBySnippetIdAndOwner_UserId(snippetId: Int?, userId: Int?): Snippet?

    @Query(value = "SELECT COUNT(*) FROM snippets s " +
            "WHERE s.title_id = :titleId AND s.owner = :owner AND s.is_private_snippet = :isPrivateSnippet", nativeQuery = true)
    fun findAllTitleUsages(@Param("titleId") titleId: Int,
                           @Param("owner") owner: Int,
                           @Param("isPrivateSnippet") isPrivateSnippet: Boolean): Int
}
