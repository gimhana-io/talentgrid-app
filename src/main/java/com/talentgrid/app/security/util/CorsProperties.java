package com.talentgrid.app.security.util;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "app.cors")
@Getter
@Setter
public class CorsProperties {

    
    private List<String> allowedOrigins;

   
    private List<String> allowedMethods;

   
    private List<String> allowedHeaders;

   
    private Boolean allowCredentials;

   
    private Long maxAge;

}
