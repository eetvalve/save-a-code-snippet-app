package com.save.codesnippetapp.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "snippets")
data class Snippet(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "snippet_id", updatable = false, nullable = false)
        val snippetId: Int = 0,

        @get: NotBlank
        val description: String = "",

        @get: NotBlank
        var snippet: String = "",

        val isPrivateSnippet: Boolean = false,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "title_id")
        val title: Title = Title(0, ""),

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "owner")
        val owner: User = User(0, "", false)
)
