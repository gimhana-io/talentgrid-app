package com.talentgrid.app.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class TalentgridSecurityConfig {

    @Bean
    SecurityFilterChain customSecurityfilterChain(HttpSecurity http) 
    {
        return http
        .csrf((csrfConfig) -> csrfConfig.disable())
        .authorizeHttpRequests((requests) -> 
            requests.requestMatchers(RegexRequestMatcher.regexMatcher(".*public$")).permitAll())
        .formLogin((flc) -> flc.disable())
        .httpBasic(withDefaults())
        .build();
    }

}
