package io.tamknown.demoApp.controller

import io.tamknown.demoApp.Book
import io.tamknown.demoApp.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/books")
class BookController (private val bookService: BookService) {

    @GetMapping
    fun getAllBooks(): List<Book?>? {
        return bookService.findAllbooks()
    }
}