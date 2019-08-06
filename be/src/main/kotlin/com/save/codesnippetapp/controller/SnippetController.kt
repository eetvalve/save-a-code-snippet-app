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

    // get title specific snippets
    @GetMapping("/snippets/{userId}/{titleId}")
    fun getSnippets(
            @PathVariable(value = "userId") userId: Int,
            @PathVariable(value = "titleId") titleId: Int): List<Snippet> =
            snippetRepository.findAllTitleSpecificSnippets(userId, titleId)


    @GetMapping("/titles/{userId}")
    fun getAllTitles(@PathVariable(value = "userId") userId: Int): List<TitleOwners.TitlesOnly>? =
            titleOwnersRepository.getAllByOwner_UserIdOrOwner_UserId(userId, PUBLIC_TITLES)


    @PostMapping("/snippet")
    fun addSnippet(@Valid @RequestBody snippet: Snippet): Snippet {

        val titleRes: Title = snippetService.createTitleIfNotExist(snippet.title)
        snippetService.createTitleOwnershipIfNotExist(titleRes, snippet)

        snippet.title.titleId = titleRes.titleId;
        return snippetRepository.save(snippet)
    }

    @PutMapping("/snippet")
    fun editSnippet(@Valid @RequestBody snippet: Snippet): Snippet {

        val titleRes: Title = snippetService.createTitleIfNotExist(snippet.title)
        snippetService.createTitleOwnershipIfNotExist(titleRes, snippet)

        snippet.title.titleId = titleRes.titleId;
        return snippetRepository.save(snippet)
    }


    @DeleteMapping("/snippet/{userId}/{snippetId}")
    fun deleteSnippet(@PathVariable userId: Int?, @PathVariable snippetId: Int?): ResponseEntity<Int> {

        val snippet: Snippet? = snippetRepository.getBySnippetIdAndOwner_UserId(snippetId, userId)

        if (snippet != null) {

            val isTitleCheckDone = snippetService.checkTitleUsages(snippet)
            if (isTitleCheckDone) {
                return snippetService.deleteSnippet(snippet.snippetId)
            }

        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
