package io.tamknown.demoApp.security

enum class ApplicationUserPermission(val permission: String) {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    BOOK_READ("book:read"),
    BOOK_WRITE("book:write");
}
