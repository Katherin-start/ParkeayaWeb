package com.parkea.ya.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parkea.ya.model.UrbanMobility;

@Repository
public interface UrbanMobilityRepository extends JpaRepository<UrbanMobility, Long> {
    
  
    List<UrbanMobility> findByZonaUrbana(String zonaUrbana);
    

    Optional<UrbanMobility> findFirstByZonaUrbanaOrderByFechaAnalisisDesc(String zonaUrbana);
    

    List<UrbanMobility> findByFechaAnalisisBetween(LocalDate inicio, LocalDate fin);
    

    List<UrbanMobility> findByNivelCongestionGreaterThan(Double nivelCongestion);
    

    List<UrbanMobility> findByDemandaEstacionamientoGreaterThan(Double demandaEstacionamiento);
    
    @Query("SELECT AVG(u.nivelTrafico), AVG(u.nivelCongestion), AVG(u.demandaEstacionamiento) " +
           "FROM UrbanMobility u WHERE u.zonaUrbana = :zona")
    Object[] findPromediosByZona(@Param("zona") String zona);

    List<UrbanMobility> findByIndiceMovilidadSostenibleGreaterThanOrderByIndiceMovilidadSostenibleDesc(Double indice);
    

    Long countByZonaUrbana(String zonaUrbana);
}