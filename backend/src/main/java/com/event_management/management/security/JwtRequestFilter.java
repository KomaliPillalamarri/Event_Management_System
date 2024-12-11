package com.event_management.management.security;

import com.event_management.management.helpers.ResponseHelper;
import com.event_management.management.model.User;
import com.event_management.management.repository.UserRepository;
import com.event_management.management.service.AuthService;
import com.event_management.management.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try{
            String requestURI = request.getRequestURI();
            boolean isProtected = !requestURI.startsWith("/api/auth/");

            final String authorizationHeader = request.getHeader("Authorization");
            String username = null;
            String jwt = null;
            if(isProtected && authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt  = authorizationHeader.substring(7);
                try{
                    username = jwtUtil.extractUsername(jwt);

                }catch (io.jsonwebtoken.ExpiredJwtException e){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token expired");
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized");
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Fetch the user details from the authentication service
                User userDetails = authService.getUserByUsername(username);

                // Extract the role from the token
                String role = jwtUtil.extractRoles(jwt);
                System.out.println("Role extracted from token: " + role);

                if (role != null) {
                    // Create a SimpleGrantedAuthority for the role
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

                    // Create an authentication token
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, List.of(authority));

                    // Set the authentication token in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            chain.doFilter(request,response);
        }catch (Exception e){
            throw e;
        }
    }


}
