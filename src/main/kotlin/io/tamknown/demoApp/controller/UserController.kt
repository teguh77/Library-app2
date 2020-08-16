package io.tamknown.demoApp.controller

import io.tamknown.demoApp.User
import io.tamknown.demoApp.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/register")
class UserController (private val userService: UserService) {
//
    @PostMapping
    fun createUser(@RequestBody user: User) {
        userService.registerUser(user)
    }
}