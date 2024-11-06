package com.backend.autos_pruebas.repositories;

import com.backend.autos_pruebas.models.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
}

