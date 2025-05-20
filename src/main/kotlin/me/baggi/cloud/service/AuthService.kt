package me.baggi.cloud.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val userService: UserService,
    private val authManager: AuthenticationManager,
    private val securityContextRepository: SecurityContextRepository,
) {
    fun authenticate(username: String, password: String, request: HttpServletRequest, response: HttpServletResponse) {
        val token =
            UsernamePasswordAuthenticationToken(username, password)
        val authentication: Authentication? = authManager.authenticate(token)
        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authentication
        SecurityContextHolder.setContext(context)
        securityContextRepository.saveContext(context, request, response)
    }

}