package io.tamknown.demoApp.controller

import io.tamknown.demoApp.Book
import io.tamknown.demoApp.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/management/api/books")
class BookManagementController (private val bookService: BookService) {

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    fun getAllBooks(): List<Book?>? {
        return bookService.findAllbooks()
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    fun getBook(@PathVariable("id") id: Int): Optional<Book> {
        return bookService.findBook(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('book:write')")
    fun addBook(@RequestBody book: Book) {
        bookService.addBook(book)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('book:write')")
    fun updateBook(@PathVariable("id") id: Int, @RequestBody book: Book) {
        bookService.updateBook(id, book)
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('book:write')")
    fun deleteBook(@PathVariable("id") id: Int?) {
        bookService.deleteBook(id!!)
    }
}