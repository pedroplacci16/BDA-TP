package com.agencia.empleadosclientes.controllers;

import com.agencia.empleadosclientes.models.Interesado;
import com.agencia.empleadosclientes.services.InteresadoService;
import com.agencia.empleadosclientes.customException.InvalidLicenseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interesados")
public class InteresadoController {
    @Autowired
    private InteresadoService interesadoService;

    @GetMapping
    public List<Interesado> getAllInteresados() {
        return interesadoService.getAllInteresados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interesado> getInteresadoById(@PathVariable Long id) {
        Interesado interesado = interesadoService.getInteresadoById(id);
        return ResponseEntity.ok(interesado);
    }

    @PostMapping
    public ResponseEntity<?> createInteresado(@RequestBody Interesado interesado) {
        try {
            Interesado newInteresado = interesadoService.saveInteresado(interesado);
            return ResponseEntity.status(HttpStatus.CREATED).body(newInteresado);
        } catch (InvalidLicenseException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            response.put("status", "INVALID_LICENSE");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInteresado(@PathVariable Long id, @RequestBody Interesado interesado) {
        try {
            Interesado updatedInteresado = interesadoService.updateInteresado(id, interesado);
            return ResponseEntity.ok(updatedInteresado);
        } catch (InvalidLicenseException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            response.put("status", "INVALID_LICENSE");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInteresado(@PathVariable Long id) {
        interesadoService.deleteInteresado(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(InvalidLicenseException.class)
    public ResponseEntity<String> handleInvalidLicenseException(InvalidLicenseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

