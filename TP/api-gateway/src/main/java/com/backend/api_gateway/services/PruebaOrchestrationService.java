package com.backend.api_gateway.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PruebaOrchestrationService {
    private final RestTemplate restTemplate;
    private final String EMPLEADOS_URL = "http://localhost:8082/api";
    private final String PRUEBAS_URL = "http://localhost:8084/api";

    public PruebaOrchestrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> createPrueba(Map<String, Object> pruebaRequest) {
        // 1. Validate Empleado
        Integer idEmpleado = (Integer) pruebaRequest.get("idEmpleado");
        ResponseEntity<Object> empleadoResponse = restTemplate.getForEntity(
                EMPLEADOS_URL + "/empleados/" + idEmpleado,
                Object.class
        );

        if (!empleadoResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Empleado not found"));
        }

        // 2. Validate Interesado
        Integer idInteresado = (Integer) pruebaRequest.get("idInteresado");
        ResponseEntity<Object> interesadoResponse = restTemplate.getForEntity(
                EMPLEADOS_URL + "/interesados/" + idInteresado,
                Object.class
        );

        if (!interesadoResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Interesado not found"));
        }

        // 3. Create Prueba
        return restTemplate.postForEntity(
                PRUEBAS_URL + "/pruebas",
                pruebaRequest,
                Object.class
        );
    }
}

