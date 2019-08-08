package com.save.codesnippetapp.controller

import com.save.codesnippetapp.model.Snippet
import com.save.codesnippetapp.model.Title
import com.save.codesnippetapp.model.TitleOwners
import com.save.codesnippetapp.repository.SnippetRepository
import com.save.codesnippetapp.repository.TitleOwnersRepository
import com.save.codesnippetapp.repository.TitleRepository
import com.save.codesnippetapp.service.SnippetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.DeleteMapping

@CrossOrigin(origins = arrayOf("http://localhost:8080"))
@RestController
@RequestMapping("/api")
class SnippetController(private val titleRepository: TitleRepository,
                        private val snippetRepository: SnippetRepository,
                        private val titleOwnersRepository: TitleOwnersRepository,
                        private val snippetService: SnippetService) {

    private val PUBLIC_TITLES: Int = 1

    // get title names by regexp search input
    @GetMapping("/titleNamesList/{userId}/{name}")
    fun getTitleNamesList(
            @PathVariable(value = "userId") userId: Int,
            @PathVariable(value = "name") name: String): List<String>? {

        return titleOwnersRepository.findAllMatchingTitles(userId, name)
    }

    @GetMapping("/titles/{userId}")
    fun getAllTitles(@PathVariable(value = "userId") userId: Int): List<TitleOwners.TitlesOnly>? =
            titleOwnersRepository.getAllDistinctByOwner_UserIdOrOwner_UserIdOrderByTitle_TitleAsc(userId, PUBLIC_TITLES)

    @GetMapping("/latestSnippets/{userId}")
    fun getLatestSnippets(@PathVariable(value = "userId") userId: Int): List<Snippet>? {

        var titleFilter: Title? = null

        // find latest added snippet by user
        val latestSnippet: Snippet? = snippetRepository.findTopByOwner_UserIdOrderBySnippetIdDesc(userId)

        if (latestSnippet == null) {
            // if not found, use alphabetically first result
            val titleList: List<TitleOwners.TitlesOnly>? = getAllTitles(userId)
            if (titleList != null) {
                titleFilter = titleList.get(0).title
                println("titleFilter: $titleFilter")
            }

        } else {
            println("latestSnippet: $latestSnippet")
            titleFilter = latestSnippet.title
        }

        if (titleFilter != null) {
            return getSnippets(userId, titleFilter.titleId)
        }
        return null
    }


    // get title specific snippets
    @GetMapping("/snippets/{userId}/{titleId}")
    fun getSnippets(
            @PathVariable(value = "userId") userId: Int,
            @PathVariable(value = "titleId") titleId: Int): List<Snippet> =
            snippetRepository.findAllTitleSpecificSnippets(userId, titleId)

    @PostMapping("/snippet")
    fun addSnippet(@Valid @RequestBody snippet: Snippet): Snippet {

        println("snippetData: ${snippet.isPrivateSnippet}")
        val titleRes: Title = snippetService.createTitleIfNotExist(snippet.title)
        snippetService.createTitleOwnershipIfNotExist(titleRes, snippet)

        var snippetCopy = snippet.copy()
        println("snippetCopy: $snippetCopy")
        snippetCopy.title.titleId = titleRes.titleId;
        snippetCopy.snippet = snippetService.sanitizeUntrustedHtml(snippet.snippet, snippet.owner.userName)

        return snippetRepository.save(snippetCopy)
    }

    @PutMapping("/snippet")
    fun editSnippet(@Valid @RequestBody snippet: Snippet): Snippet {

        val titleRes: Title = snippetService.createTitleIfNotExist(snippet.title)
        snippetService.createTitleOwnershipIfNotExist(titleRes, snippet)

        snippet.title.titleId = titleRes.titleId;
        return snippetRepository.save(snippet)
    }


    @DeleteMapping("/snippet/{userId}/{snippetId}")
    fun deleteSnippet(@PathVariable userId: Int, @PathVariable snippetId: Int): ResponseEntity<Int> {

        var response: ResponseEntity<Int> = ResponseEntity(HttpStatus.NOT_FOUND)
        val snippet: Snippet? = snippetRepository.getBySnippetId(snippetId)

        if (snippet != null) {

            val allowedToDelete: Boolean = snippetService.checkSnippetOwnership(snippet, userId)
            if (allowedToDelete) {

                val isTitleCheckDone = snippetService.checkTitleUsages(snippet)
                if (isTitleCheckDone) {
                    response = snippetService.deleteSnippet(snippet.snippetId)
                }

            } else {
                response = ResponseEntity(HttpStatus.UNAUTHORIZED)
            }

        }
        return response
    }
}
