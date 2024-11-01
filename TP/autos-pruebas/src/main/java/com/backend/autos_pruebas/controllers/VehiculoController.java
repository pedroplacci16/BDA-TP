package com.backend.autos_pruebas.controllers;

import com.backend.autos_pruebas.models.Vehiculo;
import com.backend.autos_pruebas.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<Vehiculo> createVehiculo(@RequestBody Vehiculo vehiculo) {
        return new ResponseEntity<>(vehiculoService.saveVehiculo(vehiculo), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> getAllVehiculos() {
        return new ResponseEntity<>(vehiculoService.getAllVehiculos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> getVehiculoById(@PathVariable Integer id) {
        return vehiculoService.getVehiculoById(id)
                .map(vehiculo -> new ResponseEntity<>(vehiculo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/patente/{patente}")
    public ResponseEntity<Vehiculo> getVehiculoByPatente(@PathVariable String patente) {
        return vehiculoService.getVehiculoByPatente(patente)
                .map(vehiculo -> new ResponseEntity<>(vehiculo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable Integer id, @RequestBody Vehiculo vehiculo) {
        vehiculo.setId(id);
        try {
            Vehiculo updatedVehiculo = vehiculoService.updateVehiculo(vehiculo);
            return new ResponseEntity<>(updatedVehiculo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable Integer id) {
        vehiculoService.deleteVehiculo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/anio/{anio}")
    public ResponseEntity<List<Vehiculo>> getVehiculosByAnio(@PathVariable Integer anio) {
        return new ResponseEntity<>(vehiculoService.getVehiculosByAnio(anio), HttpStatus.OK);
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<Vehiculo>> getVehiculosByMarca(@PathVariable Integer marcaId) {
        return new ResponseEntity<>(vehiculoService.getVehiculosByMarca(marcaId), HttpStatus.OK);
    }

    @GetMapping("/modelo/{modeloId}")
    public ResponseEntity<List<Vehiculo>> getVehiculosByModelo(@PathVariable Integer modeloId) {
        return new ResponseEntity<>(vehiculoService.getVehiculosByModelo(modeloId), HttpStatus.OK);
    }
}
