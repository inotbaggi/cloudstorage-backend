package me.baggi.cloud.controller

import me.baggi.cloud.dto.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController {
    @GetMapping("/me")
    fun getMe(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(UserResponse(userDetails.username))
    }
}