package io.tamknown.demoApp.security

import com.google.common.collect.Sets
import io.tamknown.demoApp.security.ApplicationUserPermission.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors

enum class ApplicationUserRole(private val permissions: Set<ApplicationUserPermission>) {
    STUDENT(Sets.newHashSet<ApplicationUserPermission>()),
    ADMIN(Sets.newHashSet<ApplicationUserPermission>(
            STUDENT_READ,
            STUDENT_WRITE,
            BOOK_READ,
            BOOK_WRITE)),
    ADMINTRAINEE(Sets.newHashSet<ApplicationUserPermission>(
            STUDENT_READ,
            BOOK_READ));

    val grantedAuthorities: Set<SimpleGrantedAuthority>
        get() {
            val permissions = permissions.stream()
                    .map { permission: ApplicationUserPermission ->
                        SimpleGrantedAuthority(permission.permission) }
                    .collect(Collectors.toSet())
            permissions.add(SimpleGrantedAuthority("ROLE_$name"))
            return permissions
        }

}