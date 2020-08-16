package io.tamknown.demoApp.jwt

import com.google.common.net.HttpHeaders
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey


@Configuration
@ConfigurationProperties(prefix = "application.jwt")
class JwtConfig {
    var secretKey: String? = null
    var tokenPrefix: String? = null
    var tokenExpirationAfterDays: Long? = null

    //GET AUTHORIZATION
    val authorizationHeader: String
        get() = HttpHeaders.AUTHORIZATION
}

@Configuration
class JwtSecretKeyConfig(private val jwtConfig: JwtConfig) {

    @Bean
    fun getSecretKey(): SecretKey {
        return Keys.hmacShaKeyFor(jwtConfig.secretKey?.toByteArray())
    }
}