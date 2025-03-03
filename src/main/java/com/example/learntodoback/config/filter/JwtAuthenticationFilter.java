package com.example.learntodoback.config.filter;

import com.example.learntodoback.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        final String authHeader = request.getHeader("Authorization");

        logger.debug("Request path: {}", path);
        logger.debug("Authorization header: {}", authHeader);

        // Пропускаем preflight-запросы и публичные эндпоинты
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Проверяем наличие и формат токена
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Missing or invalid token");
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
            return;
        }

        final String jwt = authHeader.substring(7);
        final String username;

        try {
            // Извлекаем имя пользователя из токена
            username = jwtUtil.extractUsername(jwt);
            logger.debug("Extracted username: {}", username);

            // Если имя пользователя извлечено и пользователь ещё не аутентифицирован
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Проверяем валидность токена
                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("User authenticated: {}", username);
                }
            }
        } catch (Exception e) {
            logger.error("Invalid token: {}", e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token: " + e.getMessage());
            return;
        }

        // Продолжаем цепочку фильтров
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Пропускаем preflight-запросы и публичные эндпоинты
        return "OPTIONS".equalsIgnoreCase(request.getMethod()) ||
                "/api/auth/login".equals(path) ||
                "/api/auth/register".equals(path);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}