package com.save.codesnippetapp.controller

import com.save.codesnippetapp.CodeSnippetAppApplication
import com.save.codesnippetapp.model.Snippet
import com.save.codesnippetapp.model.Title
import com.save.codesnippetapp.model.User
import com.save.codesnippetapp.repository.SnippetRepository
import com.save.codesnippetapp.repository.TitleOwnersRepository
import com.save.codesnippetapp.repository.TitleRepository
import com.save.codesnippetapp.repository.UserRepository
import com.save.codesnippetapp.service.SnippetService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = arrayOf(CodeSnippetAppApplication::class, SnippetService::class))
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // cleanup h2 db after every testcase
class SnippetControllerTests {



    @Autowired
    lateinit var snippetService: SnippetService

    @Autowired
    lateinit var snippetRepository: SnippetRepository

    @Autowired
    lateinit var titleRepository: TitleRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Before
    fun addSnippetToh2Db() {
        // snippet 1
        val owner = User(1, "edu", false)
        userRepository.save(owner)
        val title = Title(1, "Intellij")
        titleRepository.save(title)


        val snippet = Snippet(
                1,
                "text to uppercase",
                "ctrl + shift + u",
                false,
                title,
                owner)
        snippetRepository.save(snippet)

        // snippet 2
        val owner2 = User(2, "keijo", true)
        userRepository.save(owner2)
        val title2 = Title(2, "word")
        titleRepository.save(title2)

        val snippet2 = Snippet(
                2,
                "copy text",
                "ctrl + c",
                true,
                title2,
                owner2)
        snippetRepository.save(snippet2)
    }

    @Test
    fun shouldFindSnippetsDependingOnTitle() {
        val intellijSnippets: List<Snippet> = snippetRepository.findAllTitleSpecificSnippets(1, 1)
        val wordSnippets: List<Snippet> = snippetRepository.findAllTitleSpecificSnippets(2, 2)

        Assert.assertEquals(intellijSnippets.size, 1)
        Assert.assertEquals(intellijSnippets.get(0).title.title, "Intellij")

        Assert.assertEquals(wordSnippets.size, 1)
        Assert.assertEquals(wordSnippets.get(0).title.title, "word")
    }

    @Test
    fun usersLatestSnippetAddedIs() {
        val edusLatestSnippet: Snippet? = snippetRepository.findTopByOwner_UserIdOrderBySnippetIdDesc(1)
        val keijosLatestSnippet: Snippet? = snippetRepository.findTopByOwner_UserIdOrderBySnippetIdDesc(2)

        Assert.assertEquals(edusLatestSnippet!!.snippetId, 1)
        Assert.assertEquals(keijosLatestSnippet!!.snippetId, 2)
    }

    @Test
    fun snippetDeleteRemovesTitleIfNoMoreSnippetsExistOnTitle() {
        val snippet: Snippet? = snippetRepository.getBySnippetId(2)

        if (snippet != null) {
            val allowedToDelete: Boolean = snippetService.checkSnippetOwnership(snippet, 2)
            if (allowedToDelete) {

                println("here?")
                val isTitleCheckDone = snippetService.checkTitleUsages(snippet)
                if (isTitleCheckDone) {
                    snippetService.deleteSnippet(snippet.snippetId)
                }
            }
        }
        val titleInTheEnd: Title? = titleRepository.getFirstByTitleIgnoreCase("word")
        Assert.assertEquals(titleInTheEnd!!.title, "word")
    }
}