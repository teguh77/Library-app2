package io.tamknown.demoApp.jwt

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.time.LocalDate
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


data class UsernamePasswordAuthenticationRequest(
        val username: String,
        val password: String) {
    constructor() : this("", "")
}


class JwtUsernamePasswordFilter(
        private val jwtConfig: JwtConfig,
        private val secretKey: SecretKey,
        private val myauth: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?,
                                       response: HttpServletResponse?): Authentication {

        try {
            val mapper = jacksonObjectMapper()
            val authenticationRequest : UsernamePasswordAuthenticationRequest? = request?.let { mapper.readValue(request.inputStream) }

            val authentication = UsernamePasswordAuthenticationToken(
                    authenticationRequest?.username,
                    authenticationRequest?.password
            )

            return myauth.authenticate(authentication)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    override fun successfulAuthentication(request: HttpServletRequest?,
                                          response: HttpServletResponse?,
                                          chain: FilterChain?,
                                          authResult: Authentication?) {

        val token: String = Jwts.builder()
                .setSubject(authResult?.name)
                .claim("authorities", authResult?.authorities)
                .setIssuedAt(Date())
                .setExpiration(java.sql.Date.valueOf(jwtConfig.tokenExpirationAfterDays?.let { LocalDate.now().plusDays(it) }))
                .signWith(secretKey)
                .compact();

        response?.addHeader(jwtConfig.authorizationHeader, jwtConfig.tokenPrefix + token)
    }
}