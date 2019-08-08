package com.save.codesnippetapp.service

import com.save.codesnippetapp.model.Snippet
import com.save.codesnippetapp.model.Title
import com.save.codesnippetapp.model.TitleOwners
import com.save.codesnippetapp.model.User
import com.save.codesnippetapp.repository.SnippetRepository
import com.save.codesnippetapp.repository.TitleOwnersRepository
import com.save.codesnippetapp.repository.TitleRepository
import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SnippetService(private val titleRepository: TitleRepository,
                     private val snippetRepository: SnippetRepository,
                     private val titleOwnersRepository: TitleOwnersRepository,
                     @Value("\${super.user}") val superUser: String) {

    fun createTitleIfNotExist(titleObj: Title): Title {
        var title: Title? = titleRepository.getFirstByTitleIgnoreCase(titleObj.title)

        if (title == null) {
            title = titleRepository.save(titleObj)
        }
        return title
    }

    fun createTitleOwnershipIfNotExist(titleObj: Title, snippet: Snippet) {

        var user: User = snippet.owner
        val PUBLIC_USER_ID = 1
        val PUBLIC_USER_NAME = "GLOBAL"

        if (snippet.isPrivateSnippet) {
            checkTitleOwnership(user, titleObj)
        } else {
            user = User(PUBLIC_USER_ID, PUBLIC_USER_NAME, false)
            checkTitleOwnership(user, titleObj)
        }
    }

    fun checkTitleOwnership(user: User, titleObj: Title) {
        val titleOwnership: TitleOwners? = titleOwnersRepository.checkIfOwnershipExist(user.userId, titleObj.titleId)

        if (titleOwnership == null) {
            val titleOwner = TitleOwners(0, titleObj, user)
            titleOwnersRepository.save(titleOwner)
        }
    }

    fun checkTitleUsages(snippet: Snippet): Boolean {

        val titleUsages: Int = snippetRepository.findAllTitleUsages(snippet.title.titleId, snippet.owner.userId, snippet.isPrivateSnippet)

        return when (titleUsages == 1) {
            true -> removeOwnerShip(snippet)
            false -> true
        }
    }

    fun removeOwnerShip(snippet: Snippet): Boolean {

        // public snippet = 1
        var snippetOwner = 1

        if (snippet.isPrivateSnippet) {
            snippetOwner = snippet.owner.userId
        }

        val res: Int? = titleOwnersRepository.deleteByOwner_UserIdAndTitle_TitleId(snippetOwner, snippet.title.titleId)

        if (res == null) {
            return false
        }
        return true
    }

    fun checkSnippetOwnership(snippet: Snippet, userId: Int): Boolean {

        var isDeleteAllowed: Boolean = false

        if (snippet.isPrivateSnippet) {
            if (snippet.owner.userId == userId) {
                isDeleteAllowed = true
            }
        } else {
            isDeleteAllowed = true
        }
        return isDeleteAllowed
    }

    fun deleteSnippet(id: Int): ResponseEntity<Int> {
        val isRemoved = snippetRepository.deleteById(id)

        return when (isRemoved == null) {
            true -> ResponseEntity(HttpStatus.NOT_FOUND)
            false -> ResponseEntity(id, HttpStatus.OK)
        }
    }

    fun sanitizeUntrustedHtml(snippet: String, userName: String): String {

        if (userName == superUser) {
            return snippet
        }

        return Jsoup.clean(snippet, Whitelist.basic())
    }
}
