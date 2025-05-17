package me.baggi.cloud.handler

import me.baggi.cloud.dto.ErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUserNotFound(): ResponseEntity<ErrorDTO> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorDTO("Incorrect username or password"))
}