package com.agencia.empleadosclientes.repositories;

import com.agencia.empleadosclientes.models.Interesado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteresadoRepository extends JpaRepository<Interesado, Long> {
}

