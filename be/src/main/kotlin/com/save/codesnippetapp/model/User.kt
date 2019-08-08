package com.save.codesnippetapp.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "Users")
data class User (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id", updatable = false, nullable = false)
        val userId: Int = 0,

        @get: NotBlank
        val userName: String = "",

        val privateSnippets: Boolean = false

        )
