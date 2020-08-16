package io.tamknown.demoApp.controller

import io.tamknown.demoApp.Student
import io.tamknown.demoApp.service.StudentService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/management/api/students")
class StudentManagementController (private val studentService: StudentService) {


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    fun getAllBooks(): List<Student?>? {
        return studentService.findAllStudents()
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    fun getBook(@PathVariable("id") id: Int): Optional<Student> {
        return studentService.findStudent(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('student:write')")
    fun addBook(@RequestBody student: Student) {
        studentService.addStudent(student)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('student:write')")
    fun updateBook(@PathVariable("id") id: Int, @RequestBody student: Student) {
        studentService.updateStudent(id, student)
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('student:write')")
    fun deleteBook(@PathVariable("id") id: Int?) {
        studentService.deleteStudent(id!!)
    }
}