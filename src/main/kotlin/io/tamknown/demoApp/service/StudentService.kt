package io.tamknown.demoApp.service

import io.tamknown.demoApp.Student
import io.tamknown.demoApp.StudentRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudentService(private val studentRepository: StudentRepository) {

    fun addStudent(student: Student) {
        studentRepository.save(student)
    }

    fun findAllStudents() : List<Student> {
        return studentRepository.findAll()
    }

    fun findStudent(id: Int): Optional<Student> {
        return studentRepository.findById(id)
    }

    fun updateStudent(id: Int, studentUpdate: Student) {
        studentRepository.findById(id)
                .map { student: Student ->
                    student.name = studentUpdate.name
                    studentRepository.save(student)
                }
                .orElseGet {
                    throw IllegalAccessError("Spesified Id is not found")
                }
    }

    fun deleteStudent(id: Int) {
        studentRepository.deleteById(id)
    }

}