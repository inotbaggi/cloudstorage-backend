package me.baggi.cloud.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter


@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http.csrf { it.disable() }
            .cors {}
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/api/auth/sign-up", "/api/auth/sign-in",
                    "/swagger-ui/**", "/swagger-ui.html**", "/v3/api-docs/**"
                ).permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.maximumSessions(1)
                    .expiredUrl("/api/auth/sign-in")
            }
            .securityContext { it.securityContextRepository(securityContextRepository()) }
            .formLogin { it.disable() }
            //.exceptionHandling { it.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) }
            .logout {
                it.apply {
                    logoutUrl("/api/auth/sign-out")
                    addLogoutHandler { _, _, _ ->
                        HeaderWriterLogoutHandler(
                            ClearSiteDataHeaderWriter(
                                ClearSiteDataHeaderWriter.Directive.COOKIES
                            )
                        )
                    }
                    logoutSuccessHandler { request, response, authentication ->
                        response.status = HttpStatus.NO_CONTENT.value()
                    }
                }
            }
            .build()

    @Bean
    fun securityContextRepository(): SecurityContextRepository {
        return HttpSessionSecurityContextRepository()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}