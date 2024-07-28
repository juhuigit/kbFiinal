package com.example.kbfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/h2-console/**").permitAll() // H2 콘솔 URL 허용
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll() // Swagger URL 허용
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()) // H2 콘솔을 사용하려면 CSRF를 비활성화해야 합니다.
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // H2 콘솔을 사용하려면 X-Frame-Options를 비활성화해야 합니다.
                .formLogin((form) -> form
                        .permitAll()
                )
                .logout((logout) -> logout
                        .permitAll()
                );

        return http.build();
    }
}


