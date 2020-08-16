package io.tamknown.demoApp.auth

import io.tamknown.demoApp.User
import io.tamknown.demoApp.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class ApplicationUserDetailService (private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user : User = userRepository.findByUsername(username) ?:
        throw UsernameNotFoundException("User $username not found")

        return ApplicationUser(user)
    }
}