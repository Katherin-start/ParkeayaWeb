package com.parkea.ya.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Propiedades de configuración para la API de Django
 * Se carga desde application.properties
 */
@Component
@ConfigurationProperties(prefix = "django.api")
@EnableConfigurationProperties
public class DjangoApiProperties {
    
    private String baseUrl = "http://localhost:8000";
    private String token = "";
    
    public String getBaseUrl() {
        return baseUrl;
    }
    
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
}
