package com.parkea.ya.service;


import com.parkea.ya.dto.SolicitudAccesoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class DjangoApiService {
    
    private static final Logger logger = LoggerFactory.getLogger(DjangoApiService.class);
    
    @Value("${django.api.url:http://localhost:8000/api}")
    private String djangoApiUrl;
    
    private final RestTemplate restTemplate;
    
    public DjangoApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public ResponseEntity<Map<String, Object>> enviarSolicitudAcceso(SolicitudAccesoDto solicitud) {
        try {
            // Preparar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
            
            // Preparar el cuerpo de la solicitud como JSON string
            String jsonBody = String.format(
                "{\"nombre\":\"%s\",\"email\":\"%s\",\"telefono\":\"%s\",\"empresa\":\"%s\",\"mensaje\":\"%s\"}",
                escaparJson(solicitud.getNombre()),
                escaparJson(solicitud.getEmail()),
                escaparJson(solicitud.getTelefono()),
                escaparJson(solicitud.getEmpresa()),
                escaparJson(solicitud.getMensaje())
            );
            
            logger.info("Datos enviados a Django (JSON): {}", jsonBody);
            
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
            
            // URL del endpoint de Django
            String url = djangoApiUrl + "/users/solicitudes/owner/solicitar/";
            
            logger.info("Enviando solicitud a Django API: {}", url);
            
            // Realizar la petición POST
            @SuppressWarnings("unchecked")
            ResponseEntity<Map<String, Object>> response = (ResponseEntity<Map<String, Object>>) (ResponseEntity<?>) restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                requestEntity, 
                Map.class
            );
            
            // Procesar respuesta
            if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> successResponse = new HashMap<>();
                successResponse.put("success", true);
                successResponse.put("message", "Solicitud enviada correctamente");
                successResponse.put("data", response.getBody());
                
                logger.info("Solicitud enviada exitosamente: {}", response.getBody());
                return ResponseEntity.ok(successResponse);
                
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("error", "Error al enviar la solicitud");
                errorResponse.put("details", response.getBody());
                
                logger.error("Error en la respuesta de Django: {} - Body: {}", response.getStatusCode(), response.getBody());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            
        } catch (org.springframework.web.client.HttpClientErrorException.BadRequest e) {
            logger.error("Error 400 Bad Request de Django: {}", e.getResponseBodyAsString(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Datos inválidos: " + e.getResponseBodyAsString());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            
        } catch (Exception e) {
            logger.error("Error al comunicarse con Django API: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Error de conexión con el servidor: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Escapa caracteres especiales en JSON
     */
    private String escaparJson(String valor) {
        if (valor == null) {
            return "";
        }
        return valor
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }
}