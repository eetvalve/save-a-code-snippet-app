package com.save.codesnippetapp.service

import com.save.codesnippetapp.model.Title
import com.save.codesnippetapp.repository.SnippetRepository
import com.save.codesnippetapp.repository.TitleRepository
import org.springframework.stereotype.Service

@Service
class SnippetService (private val titleRepository: TitleRepository,
                      private val snippetRepository: SnippetRepository) {

}