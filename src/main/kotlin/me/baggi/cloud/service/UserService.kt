package me.baggi.cloud.service

import jakarta.transaction.Transactional
import me.baggi.cloud.dto.UserDTO
import me.baggi.cloud.exception.UserAlreadyExistException
import me.baggi.cloud.model.User
import me.baggi.cloud.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun findByUsername(username: String): User = userRepository.findByUsername(username)
        ?: throw UsernameNotFoundException("User $username not found")

    fun create(userDto: UserDTO): User {
        try {
            val user = User(username = userDto.username, password = passwordEncoder.encode(userDto.password))
            return userRepository.save(user)
        } catch (_: RuntimeException) {
            throw UserAlreadyExistException("User ${userDto.username} already exists")
        }
    }
}