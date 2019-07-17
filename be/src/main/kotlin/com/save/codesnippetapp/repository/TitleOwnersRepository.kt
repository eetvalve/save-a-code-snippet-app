package com.save.codesnippetapp.repository

import com.save.codesnippetapp.model.Title
import com.save.codesnippetapp.model.TitleOwners
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TitleOwnersRepository : JpaRepository<TitleOwners, Int> {
    fun getAllByOwner_UserIdOrOwner_UserId(owner: Int, publicTitles: Int): List<TitleOwners.TitlesOnly>?

    @Query(value = "SELECT * FROM title_owners t WHERE t.owner = :owner AND t.title_id = :title ", nativeQuery = true)
    fun checkIfOwnershipExist(@Param("owner") owner: Int, @Param("title") title: Int): TitleOwners?

    @Query(value = "SELECT DISTINCT i.title FROM title_owners t" +
            " INNER JOIN titles i ON t.title_id = i.title_id WHERE t.owner = :owner AND i.title LIKE CONCAT('%',:title,'%') OR t.owner = 1 AND i.title LIKE CONCAT('%',:title,'%')", nativeQuery = true)
    fun findAllMatchingTitles(@Param("owner") owner: Int, @Param("title") title: String): List<String>?


   // mysql> select titles.title from title_owners INNER JOIN titles ON title_owners.title_id = titles.title_id WHERE title_owners.owner = 3;


}
