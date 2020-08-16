package io.tamknown.demoApp.service

import io.tamknown.demoApp.User
import io.tamknown.demoApp.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun registerUser(user: User) {
        val existUser: User? = userRepository.findByUsername(user.username)
        existUser?.let { "Username already Exsist" }
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)
    }

}