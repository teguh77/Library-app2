package io.tamknown.demoApp.security

import io.tamknown.demoApp.auth.ApplicationUserDetailService
import io.tamknown.demoApp.jwt.JwtConfig
import io.tamknown.demoApp.jwt.JwtUsernamePasswordFilter
import io.tamknown.demoApp.jwt.JwtUsernamePasswordVerifier
import io.tamknown.demoApp.security.ApplicationUserRole.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.crypto.SecretKey


@Configuration
class PasswordConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(10)
    }
}


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class ApplicationSecurityConfig(private val applicationUserDetailService: ApplicationUserDetailService,
                                private val passwordEncoder: PasswordEncoder,
                                private val jwtConfig: JwtConfig,
                                private val secretKey: SecretKey) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(JwtUsernamePasswordFilter(jwtConfig, secretKey, authenticationManager()))
                .addFilterAfter(JwtUsernamePasswordVerifier(jwtConfig, secretKey), UsernamePasswordAuthenticationFilter::class.java)
                .authorizeRequests()
                .antMatchers("/", "/index", "/register", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name)
                .anyRequest()
                .authenticated()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoProvider())

//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder)
//                .withUser("dio")
//                .password(passwordEncoder.encode("pass"))
//                .roles(STUDENT.name)
//                .and()
//                .withUser("tama")
//                .password(passwordEncoder.encode("pass"))
//                .roles(ADMIN.name)
//                .authorities("ROLE_" + ADMIN.name,
//                        "student:read",
//                        "student:write",
//                        "book:read",
//                        "book:write")
    }

    @Bean
    fun daoProvider() : DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(applicationUserDetailService)
        provider.setPasswordEncoder(passwordEncoder)
        return provider
    }
}