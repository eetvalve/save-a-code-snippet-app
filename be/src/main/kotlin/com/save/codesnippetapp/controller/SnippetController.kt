package com.save.codesnippetapp.controller

import com.save.codesnippetapp.model.Snippet
import com.save.codesnippetapp.model.Title
import com.save.codesnippetapp.repository.SnippetRepository
import com.save.codesnippetapp.repository.TitleRepository
import com.save.codesnippetapp.service.SnippetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class SnippetController(private val titleRepository: TitleRepository,
                        private val snippetRepository: SnippetRepository,
                        private val snippetService: SnippetService) {

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
}