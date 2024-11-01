package com.backend.autos_pruebas.repositories;

import com.backend.autos_pruebas.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    Optional<Vehiculo> findByPatente(String patente);
    List<Vehiculo> findByAnio(Integer anio);
    List<Vehiculo> findByModelo_Id(Integer modeloId);
    List<Vehiculo> findByModelo_Marca_Id(Integer marcaId);
}

