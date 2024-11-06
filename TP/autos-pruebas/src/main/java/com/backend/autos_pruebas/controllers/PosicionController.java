package com.backend.autos_pruebas.controllers;

import com.backend.autos_pruebas.models.Posicion;
import com.backend.autos_pruebas.services.PosicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posiciones")
public class PosicionController {

    @Autowired
    private PosicionService posicionService;

    @PostMapping
    public ResponseEntity<Posicion> createPosicion(@RequestBody Posicion posicion) {
        return new ResponseEntity<>(posicionService.savePosicion(posicion), HttpStatus.CREATED);
    }
}

