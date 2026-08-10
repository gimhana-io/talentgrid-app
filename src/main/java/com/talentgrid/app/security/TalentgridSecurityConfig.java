package com.talentgrid.app.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class TalentgridSecurityConfig {

    @Qualifier("publicPaths")
    private final List<String> publicPaths;

    @Qualifier("securedPaths")
    private final List<String> securedPaths;

    @Bean
    SecurityFilterChain customSecurityfilterChain(HttpSecurity http) 
    {
        return http
        .csrf((csrfConfig) -> csrfConfig.disable())
        .authorizeHttpRequests(requests -> 
        {
            publicPaths.forEach(path -> requests.requestMatchers(path).permitAll());
            securedPaths.forEach(path -> requests.requestMatchers(path).authenticated());
            requests.anyRequest().denyAll();
        })
        .formLogin((flc) -> flc.disable())
        .httpBasic(withDefaults())
        .build();
    }

}
