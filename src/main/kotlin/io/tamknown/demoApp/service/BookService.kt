package io.tamknown.demoApp.service

import io.tamknown.demoApp.Book
import io.tamknown.demoApp.BookRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(private val bookRepository: BookRepository) {


    fun addBook(book: Book) {
        bookRepository.save(book)
    }

    fun findAllbooks () : List<Book> {
        return bookRepository.findAll()
    }

    fun findBook(id: Int): Optional<Book> {
        return bookRepository.findById(id)
    }

    fun updateBook(id: Int, bookUpdate: Book) {
        bookRepository.findById(id)
                .map { book: Book ->
                    book.title = bookUpdate.title
                    book.studentId = bookUpdate.studentId
                    bookRepository.save(book)
                }
                .orElseGet{
                    throw IllegalAccessError("Spesified Id is not found")
                }
    }

    fun deleteBook(id: Int) {
        bookRepository.deleteById(id)
    }

}