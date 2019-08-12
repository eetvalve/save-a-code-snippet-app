package com.save.codesnippetapp

import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.DatastoreOptions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class CodeSnippetAppApplication

fun main(args: Array<String>) {
    runApplication<CodeSnippetAppApplication>(*args)
}

@Bean
fun cloudDatastoreService() : Datastore? {
        return DatastoreOptions.getDefaultInstance().getService()
    }
