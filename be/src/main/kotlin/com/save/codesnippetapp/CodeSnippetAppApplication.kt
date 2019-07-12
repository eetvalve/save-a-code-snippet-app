package com.save.codesnippetapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CodeSnippetAppApplication

fun main(args: Array<String>) {
    runApplication<CodeSnippetAppApplication>(*args)
}
