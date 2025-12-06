package com.parkea.ya.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) para solicitudes de acceso al panel
 * Mapea los datos del formulario web hacia Django
 */
public class SolicitudAccesoDto {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 9, max = 20, message = "El teléfono debe tener entre 9 y 20 caracteres")
    private String telefono;
    
    @Size(max = 200, message = "La empresa no puede exceder 200 caracteres")
    private String empresa;
    
    @Size(max = 500, message = "El mensaje no puede exceder 500 caracteres")
    private String mensaje;

    /** NUEVOS CAMPOS */
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 300, message = "La dirección no puede exceder 300 caracteres")
    private String direccion;

    private Integer capacidad;  // capacidad total de espacios

    // Constructores
    public SolicitudAccesoDto() {}

    public SolicitudAccesoDto(String nombre, String email, String telefono, String empresa) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.empresa = empresa;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
}
