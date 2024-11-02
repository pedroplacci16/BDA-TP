package com.backend.autos_pruebas.controllers;

import com.backend.autos_pruebas.models.Prueba;
import com.backend.autos_pruebas.services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {
    @Autowired
    private PruebaService pruebaService;

    @PostMapping
    public ResponseEntity<?> registrarPrueba(@RequestBody Prueba prueba) {
        try {
            return ResponseEntity.ok(pruebaService.registrarPrueba(prueba));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/en-curso")
    public ResponseEntity<List<Prueba>> getPruebasEnCurso(
//            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date momento) {

        Date momentoConsulta = momento != null ? momento : new Date();
        System.out.println(momento);
//        LocalDateTime momentoConsulta = momento != null ? momento : LocalDateTime.now();
        return ResponseEntity.ok(pruebaService.getPruebasEnCurso(momentoConsulta));
    }




    @GetMapping
        public ResponseEntity<List<Prueba>> getAllPruebas() {
            return ResponseEntity.ok(pruebaService.getAllPruebas());
        }



}


