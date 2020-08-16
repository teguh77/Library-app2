package io.tamknown.demoApp.controller

import io.tamknown.demoApp.Student
import io.tamknown.demoApp.service.StudentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/students")
class StudentController (private val studentService: StudentService) {

    @GetMapping
    fun getAllStudents(): List<Student?>? {
        return studentService.findAllStudents()
    }
}