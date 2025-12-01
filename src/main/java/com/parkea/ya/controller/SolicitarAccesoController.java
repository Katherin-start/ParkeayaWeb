package com.parkea.ya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller que redirige /solicitar-acceso a /solicitud-acceso
 */
@Controller
@RequestMapping("/solicitar-acceso")
public class SolicitarAccesoController {
    
    @GetMapping
    public RedirectView redirigir() {
        return new RedirectView("/solicitud-acceso");
    }
}
