package com.parkea.ya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parkea.ya.dto.AIRequest;
import com.parkea.ya.dto.AIResponse;
import com.parkea.ya.service.GeminiAIService;

@RestController
@RequestMapping("/api/ai")
public class AIController {
    
    @Autowired
    private GeminiAIService geminiAIService;
    
    @PostMapping("/generar")
    public AIResponse generarContenido(@RequestBody AIRequest request) {
        return geminiAIService.generarContenido(request);
    }
    
    @GetMapping("/vision-futuro")
    public AIResponse getVisionFuturoMovilidad() {  // ← CORREGIDO
        return geminiAIService.generarVisionFuturoMovilidad();
    }
    
    @GetMapping("/beneficios")
    public AIResponse getBeneficiosEstacionamiento() { 
        return geminiAIService.generarBeneficiosEstacionamiento();
    }
    
    @GetMapping("/prediccion")
    public AIResponse getPrediccionDemanda(@RequestParam(defaultValue = "centro urbano") String zona) { 
        return geminiAIService.generarPrediccionDemanda(zona);
    }
    
    @GetMapping("/idea")
    public AIResponse generarIdea(@RequestParam String tema) {  
        return geminiAIService.generarIdeaPersonalizada(tema);
    }
    
    @GetMapping("/test")
    public AIResponse testAPI() {  
        AIRequest request = new AIRequest(
            "Responde brevemente en español: ¿Está funcionando la conexión con Gemini?",
            "test"
        );
        return geminiAIService.generarContenido(request);
    }
}