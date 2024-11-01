package com.agencia.empleadosclientes.services;

import com.agencia.empleadosclientes.models.Empleado;
import com.agencia.empleadosclientes.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    public Empleado findById(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado not found with id: " + id));
    }

    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado update(Long id, Empleado empleadoDetails) {
        Empleado empleado = findById(id);
        empleado.setNombre(empleadoDetails.getNombre());
        empleado.setApellido(empleadoDetails.getApellido());
        //continue updating the fields
        return empleadoRepository.save(empleado);
    }

    public void delete(Long id) {
        Empleado empleado = findById(id);
        empleadoRepository.delete(empleado);
    }
}

