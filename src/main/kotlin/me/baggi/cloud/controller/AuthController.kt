package me.baggi.cloud.controller

import jakarta.servlet.http.HttpServletRequest
import me.baggi.cloud.dto.UserDTO
import me.baggi.cloud.dto.UserResponse
import me.baggi.cloud.service.AuthService
import me.baggi.cloud.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val userService: UserService
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody body: UserDTO, request: HttpServletRequest): ResponseEntity<UserResponse> {
        val user = userService.create(body)
        authService.authenticate(body.username, body.password, request)
        return ResponseEntity.ok(UserResponse(user.username))
    }


    @PostMapping("/sign-in")
    fun signIn(@RequestBody body: UserDTO, request: HttpServletRequest): ResponseEntity<UserResponse> {
        val user = userService.findByUsername(body.username)
        authService.authenticate(body.username, body.password, request)
        return ResponseEntity.ok(UserResponse(user.username))
    }
}