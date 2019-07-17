package com.save.codesnippetapp.service

import com.save.codesnippetapp.model.Snippet
import com.save.codesnippetapp.model.Title
import com.save.codesnippetapp.model.TitleOwners
import com.save.codesnippetapp.model.User
import com.save.codesnippetapp.repository.SnippetRepository
import com.save.codesnippetapp.repository.TitleOwnersRepository
import com.save.codesnippetapp.repository.TitleRepository
import org.springframework.stereotype.Service

@Service
class SnippetService(private val titleRepository: TitleRepository,
                     private val snippetRepository: SnippetRepository,
                     private val titleOwnersRepository: TitleOwnersRepository) {

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
}
