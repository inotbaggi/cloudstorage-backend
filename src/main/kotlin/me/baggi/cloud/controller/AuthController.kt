package me.baggi.cloud.controller

import me.baggi.cloud.dto.request.UserLoginRequest
import me.baggi.cloud.dto.request.UserRegisterRequest
import me.baggi.cloud.dto.response.UserLoginResponse
import me.baggi.cloud.dto.response.UserRegisterResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody userRegisterRequest: UserRegisterRequest): ResponseEntity<UserRegisterResponse> {

    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody userLoginRequest: UserLoginRequest): ResponseEntity<UserLoginResponse> {

    }

    @PostMapping("/sign-out")
    fun signOut(): ResponseEntity<UserLoginResponse> {}
}