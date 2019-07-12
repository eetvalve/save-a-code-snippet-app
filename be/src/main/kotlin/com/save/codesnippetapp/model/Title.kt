package com.save.codesnippetapp.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "titles")
data class Title (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "title_id", updatable = false, nullable = false)
        var titleId: Int = 0,

        @get: NotBlank
        val title: String = ""
)