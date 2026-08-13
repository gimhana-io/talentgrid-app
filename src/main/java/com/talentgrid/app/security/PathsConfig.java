package com.talentgrid.app.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathsConfig {
    @Bean(name = "publicPaths")
    public List<String> publicPaths() {
        return List.of(
                "/api/companies/public",
                "/api/contacts/public",
                "/api/auth/login/public",
                "/api/auth/register/public",
                "/api/csrf-token/public",
                "/api/swagger-ui.html",
                "/swagger-ui/**",
                "/api/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/webjars/**"
        );
    }

    @Bean(name = "securedPaths")
    public List<String> securedPaths() {
        return List.of(
                "/api/**"
        );
    }

    @Bean(name = "adminPaths")
    public List<String> adminPaths() {
        return List.of(
                "/api/contacts/admin"
        );
    }
}
