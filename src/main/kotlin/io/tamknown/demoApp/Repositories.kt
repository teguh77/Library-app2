package io.tamknown.demoApp

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String): User?
}
interface StudentRepository : JpaRepository<Student, Int>
interface BookRepository : JpaRepository<Book, Int>
