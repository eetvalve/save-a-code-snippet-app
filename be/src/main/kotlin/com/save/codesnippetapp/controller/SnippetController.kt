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

@RestController
@RequestMapping("/api")
class SnippetController(private val titleRepository: TitleRepository,
                        private val snippetRepository: SnippetRepository,
                        private val titleOwnersRepository: TitleOwnersRepository,
                        private val snippetService: SnippetService) {

    private val PUBLIC_TITLES: Int = 1

    /*
    @GetMapping("/titles")
    fun getAllTitles(): List<Title> = titleRepository.findAll()


    @GetMapping("/titleNamesList/{name}")
    fun getTitleNamesList(@PathVariable(value = "name") name: String): List<String> {
        val titlesRes: List<Title> = titleRepository.findByTitleContaining(name)
        val titleNames: MutableList<String> = mutableListOf<String>()

        for (title in titlesRes) {
            titleNames.add(title.title)
        }

        return titleNames
    }

    @GetMapping("/snippets/{id}")
    fun getSnippets(@PathVariable(value = "id") titleId: Int): List<Snippet> =
            snippetRepository.getAllByTitleTitleId(titleId)


    @PostMapping("/addSnippet")
    fun addSnippet(@Valid @RequestBody snippet: Snippet): Snippet? {

        var titleRes: Title? = titleRepository.getFirstByTitleIgnoreCase(snippet.title.title)

        // if not exists -> create
        if (titleRes == null) {
           titleRes = titleRepository.save(snippet.title)
        }

        snippet.title.titleId = titleRes.titleId;
        return snippetRepository.save(snippet)
    }
    */

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


    @PostMapping("/addSnippet")
    fun addSnippet(@Valid @RequestBody snippet: Snippet): Snippet {

        val titleRes: Title = snippetService.createTitleIfNotExist(snippet.title)
        snippetService.createTitleOwnershipIfNotExist(titleRes, snippet)

        snippet.title.titleId = titleRes.titleId;
        return snippetRepository.save(snippet)
    }

    // todo edit, delete
}
