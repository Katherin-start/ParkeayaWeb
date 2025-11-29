package com.parkea.ya.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parkea.ya.entity.SolicitudAcceso;

@Repository
public interface SolicitudRepository extends JpaRepository<SolicitudAcceso, Long> {


    List<SolicitudAcceso> findByEmail(String email);

    List<SolicitudAcceso> findByEstado(SolicitudAcceso.EstadoSolicitud estado);
    
    List<SolicitudAcceso> findByEstadoOrderByFechaSolicitudAsc(SolicitudAcceso.EstadoSolicitud estado);
    
 
    List<SolicitudAcceso> findByFechaSolicitudBetween(LocalDateTime inicio, LocalDateTime fin);
    

    List<SolicitudAcceso> findByCiudadIgnoreCase(String ciudad);
    

    @Query("SELECT s FROM SolicitudAcceso s WHERE " +
           "6371 * acos(cos(radians(:lat)) * cos(radians(s.latitud)) * " +
           "cos(radians(s.longitud) - radians(:lng)) + sin(radians(:lat)) * " +
           "sin(radians(s.latitud))) < :radio")
    List<SolicitudAcceso> findNearLocation(@Param("lat") Double latitud, 
                                          @Param("lng") Double longitud, 
                                          @Param("radio") Double radioKm);
    

    Long countByEstado(SolicitudAcceso.EstadoSolicitud estado);
    

    Optional<SolicitudAcceso> findFirstByEmailOrderByFechaSolicitudDesc(String email);
}