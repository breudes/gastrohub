package com.breudes.gastrohub.infrastructure.security;

import com.breudes.gastrohub.infrastructure.security.token.TokenService;
import com.breudes.gastrohub.model.User;
import com.breudes.gastrohub.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var username = tokenService.getSubject(token);
            if (username!=null) {
                Optional<User> user = userRepository.findByUsername(username);
                UsernamePasswordAuthenticationToken authentication;
                if (user.isPresent()) {
                    authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.get().getAuthorities()
                    );
                } else {
                    throw new RuntimeException("User not found to authenticate.");
                }
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
