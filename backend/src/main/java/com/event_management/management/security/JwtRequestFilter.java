package com.event_management.management.security;

import com.event_management.management.helpers.ResponseHelper;
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

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try{
            String requestURI = request.getRequestURI();
            boolean isProtected = !requestURI.startsWith("/api/auth/");

            final String authorizationHeader = request.getHeader("Authorization");
            String username = null;
            String jwt = null;
            String role = null;
            if(isProtected && authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt  = authorizationHeader.substring(7);
                try{
                    username = jwtUtil.extractUsername(jwt);
                    role = jwtUtil.extractRole(jwt).toUpperCase();

                }catch (io.jsonwebtoken.ExpiredJwtException e){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token expired");
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized");
                }
            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,null, List.of(new SimpleGrantedAuthority("ROLE_"+role)));
                SecurityContextHolder.getContext().setAuthentication((authToken));
            }

            chain.doFilter(request,response);
        }catch (Exception e){
            throw e;
        }
    }


}
