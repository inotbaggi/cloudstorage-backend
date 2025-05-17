package me.baggi.cloud.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Base64
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.crypto.SecretKey


@Service
class JWTService {
    @Value("\${jwt.secret}")
    private var jwtSecret: String = "secret"

    @Value("\${jwt.access-token-life-millis}")
    private var accessTokenTime = TimeUnit.DAYS.toMillis(7)

    @Value("\${jwt.refresh-token-life-millis}")
    private var refreshTokenTime = TimeUnit.DAYS.toMillis(7)

    fun generateAccessToken(username: String): String = generateToken(username, accessTokenTime)

    fun generateRefreshToken(username: String): String = generateToken(username, refreshTokenTime)

    fun extractExpiration(token: String): Instant = Jwts
        .parser()
        .verifyWith(getSecretKey(jwtSecret))
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .expiration.toInstant()

    fun extractUsername(token: String): String = Jwts
        .parser()
        .verifyWith(getSecretKey(jwtSecret))
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .subject

    fun isValid(token: String, user: UserDetails): Boolean {
        try {
            val username = user.username
            return username == extractUsername(token) && !isTokenExpired(token)
        } catch (_: Exception) {
            return false
        }
    }

    private fun generateToken(username: String, expirationTime: Long): String {
        return Jwts.builder()
            .subject(username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(getSecretKey(jwtSecret))
            .compact()
    }

    private fun getSecretKey(secret: String): SecretKey {
        val bytes = Base64.getDecoder().decode(secret)
        return Keys.hmacShaKeyFor(bytes)
    }

    fun isTokenExpired(token: String): Boolean = Instant.now().isAfter(extractExpiration(token))
}