package me.baggi.cloud.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint


@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http.csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) }
            .anonymous { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/api/auth/sign-up", "/api/auth/sign-in",
                    "/swagger-ui/**", "/swagger-ui.html**", "/v3/api-docs/**"
                ).permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { it.disable() }
            .exceptionHandling { it.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) }
            .logout {
                it.apply {
                    logoutSuccessUrl("/api/auth/sign-in")
                    logoutUrl("/api/auth/sign-out")
                    invalidateHttpSession(true)
                    deleteCookies("STORAGE_SESSION")
                    clearAuthentication(true)
                }
            }
            .build()

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}