package com.backend.autos_pruebas.services;

import com.backend.autos_pruebas.models.Vehiculo;
import com.backend.autos_pruebas.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    // Create
    @Transactional
    public Vehiculo saveVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    // Read all
    @Transactional(readOnly = true)
    public List<Vehiculo> getAllVehiculos() {
        return vehiculoRepository.findAll();
    }

    // Read by ID
    @Transactional(readOnly = true)
    public Optional<Vehiculo> getVehiculoById(Integer id) {
        return vehiculoRepository.findById(id);
    }

    // Read by Patente
    @Transactional(readOnly = true)
    public Optional<Vehiculo> getVehiculoByPatente(String patente) {
        return vehiculoRepository.findByPatente(patente);
    }

    // Update
    @Transactional
    public Vehiculo updateVehiculo(Vehiculo vehiculo) {
        if (vehiculoRepository.existsById(vehiculo.getId())) {
            return vehiculoRepository.save(vehiculo);
        }
        throw new RuntimeException("Vehiculo not found with id: " + vehiculo.getId());
    }

    // Delete
    @Transactional
    public void deleteVehiculo(Integer id) {
        vehiculoRepository.deleteById(id);
    }

    // Additional useful methods
    @Transactional(readOnly = true)
    public List<Vehiculo> getVehiculosByAnio(Integer anio) {
        return vehiculoRepository.findByAnio(anio);
    }

    @Transactional(readOnly = true)
    public List<Vehiculo> getVehiculosByMarca(Integer marcaId) {
        return vehiculoRepository.findByModelo_Marca_Id(marcaId);
    }

    @Transactional(readOnly = true)
    public List<Vehiculo> getVehiculosByModelo(Integer modeloId) {
        return vehiculoRepository.findByModelo_Id(modeloId);
    }
}

