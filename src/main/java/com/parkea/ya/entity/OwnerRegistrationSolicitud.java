package com.parkea.ya.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad para almacenar solicitudes de registro de propietarios
 * Esta tabla mantiene un histórico local de los registros enviados a Django
 */
@Entity
@Table(name = "owner_registration_requests")
public class OwnerRegistrationSolicitud {
    
    public enum EstadoRegistro {
        PENDIENTE("Pendiente de envío"),
        ENVIADO("Enviado a Django"),
        APROBADO("Registro aprobado"),
        RECHAZADO("Registro rechazado"),
        ERROR("Error en el envío");
        
        private final String descripcion;
        
        EstadoRegistro(String descripcion) {
            this.descripcion = descripcion;
        }
        
        public String getDescripcion() {
            return descripcion;
        }
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Column(name = "username", nullable = false, length = 150, unique = true)
    private String username;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Column(name = "email", nullable = false, length = 254, unique = true)
    private String email;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "first_name", nullable = false, length = 150)
    private String firstName;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "last_name", nullable = false, length = 150)
    private String lastName;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 9, max = 20, message = "El teléfono debe tener entre 9 y 20 caracteres")
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;
    
    @NotBlank(message = "El tipo de documento es obligatorio")
    @Column(name = "tipo_documento", nullable = false, length = 20)
    private String tipoDocumento;
    
    @NotBlank(message = "El número de documento es obligatorio")
    @Size(min = 6, max = 20, message = "El número de documento debe tener entre 6 y 20 caracteres")
    @Column(name = "numero_documento", nullable = false, length = 20)
    private String numeroDocumento;
    
    @NotBlank(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "fecha_nacimiento", nullable = false, length = 10)
    private String fechaNacimiento;
    
    @NotBlank(message = "La dirección es obligatoria")
    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;
    
    @NotBlank(message = "El código postal es obligatorio")
    @Size(max = 10, message = "El código postal no puede exceder 10 caracteres")
    @Column(name = "codigo_postal", nullable = false, length = 10)
    private String codigoPostal;
    
    @Column(name = "pais", length = 50)
    private String pais = "Perú";
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoRegistro estado = EstadoRegistro.PENDIENTE;
    
    @Column(name = "id_django", nullable = true)
    private Long idDjango;
    
    @Column(name = "mensaje_error", columnDefinition = "TEXT")
    private String mensajeError;
    
    @Column(name = "fecha_solicitud", nullable = false, updatable = false)
    private LocalDateTime fechaSolicitud = LocalDateTime.now();
    
    @Column(name = "fecha_envio", nullable = true)
    private LocalDateTime fechaEnvio;
    
    @Column(name = "fecha_actualizacion", nullable = true)
    private LocalDateTime fechaActualizacion;
    
    // Constructores
    public OwnerRegistrationSolicitud() {
        this.estado = EstadoRegistro.PENDIENTE;
        this.fechaSolicitud = LocalDateTime.now();
    }
    
    public OwnerRegistrationSolicitud(String username, String email, String firstName,
                                     String lastName, String telefono, String tipoDocumento,
                                     String numeroDocumento, String fechaNacimiento,
                                     String direccion, String codigoPostal) {
        this();
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telefono = telefono;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
    
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    
    public EstadoRegistro getEstado() { return estado; }
    public void setEstado(EstadoRegistro estado) { 
        this.estado = estado;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public Long getIdDjango() { return idDjango; }
    public void setIdDjango(Long idDjango) { this.idDjango = idDjango; }
    
    public String getMensajeError() { return mensajeError; }
    public void setMensajeError(String mensajeError) { this.mensajeError = mensajeError; }
    
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
    
    @Override
    public String toString() {
        return "OwnerRegistrationSolicitud{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", estado=" + estado +
                ", fechaSolicitud=" + fechaSolicitud +
                '}';
    }
}
