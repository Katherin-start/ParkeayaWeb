package com.parkea.ya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmacionController {

    // Simple endpoint to allow direct access via /confirmacion
    @GetMapping("/confirmacion")
    public String mostrarConfirmacion(@RequestParam(required = false) String nombre,
                                      @RequestParam(required = false) String mensaje,
                                      Model model) {
        if (nombre != null) model.addAttribute("nombre", nombre);
        if (mensaje != null) model.addAttribute("mensaje", mensaje);
        return "pages/confirmacion";
    }
}
