package com.save.codesnippetapp.model

import javax.persistence.*

@Entity
@Table(name = "title_owners")
data class TitleOwners(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", updatable = false, nullable = false)
        var id: Int = 0,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "title_id")
        val title: Title = Title(0, ""),

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "owner")
        val owner: User = User(0, "", false)

) {
     interface TitlesOnly {
        val title: Title get() = TitleOwners().title
    }
}

