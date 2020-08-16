package io.tamknown.demoApp

import javax.persistence.*

@Entity
@Table(name = "user")
data class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Int,
        @Column(unique = true)
        val username : String,
        var password : String,
        val roles : String
)

@Entity
@Table(name = "student")
data class Student(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Int,
        var name: String? = null
) {
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "student_id", referencedColumnName = "id")
        val books: List<Book>? = null
}

@Entity
@Table(name = "book")
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Int,
        var title: String? = null,
        @Column(name = "student_id")
        var studentId: Int? = 0
)