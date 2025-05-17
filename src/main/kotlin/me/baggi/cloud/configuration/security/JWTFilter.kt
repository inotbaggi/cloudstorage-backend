package me.baggi.cloud.configuration.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.baggi.cloud.service.JWTService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolder.clearContext
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.WebUtils


@Component
class JWTFilter(
    private val jwtService: JWTService,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessToken = extractTokenFromCookies(request) ?: throw Exception("Token not found")
            val user = userDetailsService.loadUserByUsername(jwtService.extractUsername(accessToken))

            if (jwtService.isValid(accessToken, user)) {
                val auth = UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
                SecurityContextHolder.getContext().authentication = auth
            } else {
                clearContext()
            }

            filterChain.doFilter(request, response)
        } catch (_: Exception) {
            clearContext()
            filterChain.doFilter(request, response)
        }
    }

    private fun extractTokenFromCookies(request: HttpServletRequest): String? {
        return WebUtils.getCookie(request, "access_token")?.value
    }
}