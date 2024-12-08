package com.event_management.management.config;

import com.event_management.management.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
