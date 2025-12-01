package com.parkea.ya.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parkea.ya.entity.OwnerRegistrationSolicitud;
import com.parkea.ya.entity.OwnerRegistrationSolicitud.EstadoRegistro;

/**
 * Repository para la entidad OwnerRegistrationSolicitud
 * Proporciona acceso a la base de datos para solicitudes de registro de owners
 */
@Repository
public interface OwnerRegistrationRepository extends JpaRepository<OwnerRegistrationSolicitud, Long> {
    
    /**
     * Busca una solicitud por email
     */
    Optional<OwnerRegistrationSolicitud> findByEmail(String email);
    
    /**
     * Busca una solicitud por username
     */
    Optional<OwnerRegistrationSolicitud> findByUsername(String username);
    
    /**
     * Obtiene todas las solicitudes con un estado específico
     */
    List<OwnerRegistrationSolicitud> findByEstado(EstadoRegistro estado);
    
    /**
     * Obtiene solicitudes por rango de fechas
     */
    @Query("SELECT o FROM OwnerRegistrationSolicitud o WHERE o.fechaSolicitud BETWEEN :inicio AND :fin")
    List<OwnerRegistrationSolicitud> findByFechaSolicitudBetween(
        @Param("inicio") LocalDateTime inicio,
        @Param("fin") LocalDateTime fin
    );
    
    /**
     * Obtiene solicitudes con error que necesitan reintento
     */
    @Query("SELECT o FROM OwnerRegistrationSolicitud o WHERE o.estado = 'ERROR' ORDER BY o.fechaSolicitud ASC")
    List<OwnerRegistrationSolicitud> findConError();
    
    /**
     * Verifica si un email ya tiene una solicitud registrada
     */
    boolean existsByEmail(String email);
    
    /**
     * Verifica si un username ya está registrado
     */
    boolean existsByUsername(String username);
}
