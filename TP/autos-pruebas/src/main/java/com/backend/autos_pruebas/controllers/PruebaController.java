package com.backend.autos_pruebas.controllers;

import com.backend.autos_pruebas.models.Prueba;
import com.backend.autos_pruebas.services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarPrueba(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> request) {
        try {
            String comentarios = (String) request.get("comentarios");
            Date fechaHoraFin = request.get("fechaHoraFin") != null ?
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse((String) request.get("fechaHoraFin")) :
                    new Date();

            Prueba prueba = pruebaService.finalizarPrueba(id, comentarios, fechaHoraFin);
            return ResponseEntity.ok(prueba);
        } catch (RuntimeException | ParseException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }






    @GetMapping
        public ResponseEntity<List<Prueba>> getAllPruebas() {
            return ResponseEntity.ok(pruebaService.getAllPruebas());
        }



}


