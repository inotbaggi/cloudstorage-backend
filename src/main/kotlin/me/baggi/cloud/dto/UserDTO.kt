package me.baggi.cloud.dto

import jakarta.validation.constraints.Min

data class UserDTO(
    @Min(4)
    val username: String,
    val password: String
)