package com.save.codesnippetapp.repository

import com.save.codesnippetapp.model.Title
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface TitleRepository : JpaRepository<Title, Int> {
    @Query(value = "SELECT * FROM titles t WHERE t.owner = :owner AND t.title LIKE CONCAT('%',:title,'%') " +
            "OR t.is_private_snippet = FALSE AND t.title LIKE CONCAT('%',:title,'%')", nativeQuery = true)
    fun findAllMatchingTitles(@Param("owner") owner: Int, @Param("title") title: String): List<Title>

    // ignoreCase means that query is case insensitive
    fun getFirstByTitleIgnoreCase(title: String): Title?
}
