package io.tamknown.demoApp.auth

import io.tamknown.demoApp.User
import io.tamknown.demoApp.security.ApplicationUserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class ApplicationUser (user: User) : UserDetails {

    private val username: String = user.username
    private val password: String = user.password
    private val authorities: Set<SimpleGrantedAuthority>? = ApplicationUserRole
            .valueOf(user.roles)
            .grantedAuthorities

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return authorities
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}