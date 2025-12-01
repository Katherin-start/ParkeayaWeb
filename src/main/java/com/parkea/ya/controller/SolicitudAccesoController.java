package com.parkea.ya.controller;


import com.parkea.ya.dto.SolicitudAccesoDto;
import com.parkea.ya.service.DjangoApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/solicitud-acceso")
public class SolicitudAccesoController {
    
    private static final Logger logger = LoggerFactory.getLogger(SolicitudAccesoController.class);
    
    private final DjangoApiService djangoApiService;
    
    public SolicitudAccesoController(DjangoApiService djangoApiService) {
        this.djangoApiService = djangoApiService;
    }
    
    // Mostrar formulario
    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("solicitudAccesoDto", new SolicitudAccesoDto());
        return "solicitud-formulario";
    }
    
    // Procesar formulario HTML
    @PostMapping("/enviar")
    public String procesarSolicitud(
            @Valid @ModelAttribute("solicitudAccesoDto") SolicitudAccesoDto solicitudAccesoDto,
            BindingResult bindingResult,
            Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Por favor corrige los errores en el formulario");
            return "solicitud-formulario";
        }
        
        try {
            ResponseEntity<Map<String, Object>> response = djangoApiService.enviarSolicitudAcceso(solicitudAccesoDto);
            
            if (response.getBody() != null && Boolean.TRUE.equals(response.getBody().get("success"))) {
                // Éxito - redirigir a página de confirmación
                model.addAttribute("titulo", "¡Solicitud Enviada Exitosamente!");
                model.addAttribute("mensaje", "Hemos recibido tu solicitud de acceso al panel de Parkea Ya. Te contactaremos en un plazo máximo de 48 horas.");
                model.addAttribute("nombre", solicitudAccesoDto.getNombre());
                model.addAttribute("email", solicitudAccesoDto.getEmail());
                
                // Render the existing Thymeleaf template under templates/pages/confirmacion.html
                return "pages/confirmacion";
            } else {
                // Error en la API
                String errorMessage = "Error al enviar la solicitud. Por favor intenta nuevamente.";
                if (response.getBody() != null && response.getBody().get("error") != null) {
                    errorMessage = response.getBody().get("error").toString();
                }
                
                model.addAttribute("error", errorMessage);
                return "solicitud-formulario";
            }
            
        } catch (Exception e) {
            logger.error("Error al procesar solicitud: {}", e.getMessage(), e);
            model.addAttribute("error", "Error de conexión. Por favor intenta más tarde.");
            return "solicitud-formulario";
        }
    }
}