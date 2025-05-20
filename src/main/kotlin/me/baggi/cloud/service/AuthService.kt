package me.baggi.cloud.service

import jakarta.servlet.http.HttpServletRequest
import me.baggi.cloud.dto.UserResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class AuthService(
    private val userService: UserService,
    private val authManager: AuthenticationManager
) {
    fun authenticate(username: String, password: String, request: HttpServletRequest) {
        val authRequest = UsernamePasswordAuthenticationToken(username, password)
        SecurityContextHolder.getContext().authentication = authManager.authenticate(authRequest)
        request.session
    }

}