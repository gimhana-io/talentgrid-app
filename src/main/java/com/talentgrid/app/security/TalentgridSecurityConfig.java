package com.talentgrid.app.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class TalentgridSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) 
    {
        return http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
        .formLogin(withDefaults())
        .httpBasic(withDefaults())
        .build();
    }

}
