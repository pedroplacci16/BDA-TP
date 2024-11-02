package com.backend.autos_pruebas.repositories;

import com.backend.autos_pruebas.models.Prueba;
import com.backend.autos_pruebas.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Long> {
    boolean existsByVehiculoAndFechaHoraInicioBetween(
            Vehiculo vehiculo,
            Date startDate,
            Date endDate
    );
    boolean existsByIdEmpleadoAndFechaHoraInicioBetween(Integer idEmpleado, Date start, Date end);
    boolean existsByIdInteresadoAndFechaHoraInicioBetween(Integer idInteresado, Date start, Date end);
    @Query("SELECT p FROM Prueba p WHERE p.fechaHoraInicio <= :momento AND p.fechaHoraFin >= :momento")
    List<Prueba> findPruebasEnCurso(Date momento);




}

