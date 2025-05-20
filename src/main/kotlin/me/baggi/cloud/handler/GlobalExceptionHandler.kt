package me.baggi.cloud.handler

import me.baggi.cloud.dto.ErrorDTO
import me.baggi.cloud.exception.UserAlreadyExistException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUserNotFound(): ResponseEntity<ErrorDTO> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorDTO("Incorrect username or password"))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(): ResponseEntity<ErrorDTO> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorDTO("Validation failed"))

    @ExceptionHandler(UserAlreadyExistException::class)
    fun handleUserAlreadyExist(): ResponseEntity<ErrorDTO> =
        ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ErrorDTO("Validation failed"))

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(): ResponseEntity<ErrorDTO> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorDTO("Incorrect username or password"))
}