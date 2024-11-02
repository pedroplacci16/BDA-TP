package com.backend.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Map;

@Configuration
public class GWConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(auth -> auth
                        .anyExchange().permitAll());
        return http.build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, RestTemplate restTemplate) {
        return builder.routes()
                .route("empleados-interesados-service", r -> r
                        .path("/api/empleados/**", "/api/interesados/**")
                        .uri("http://localhost:8082"))
                .route("pruebas-service", r -> r
                        .path("/api/pruebas")
                        .and()
                        .method("POST")
                        .filters(f -> f.modifyRequestBody(Object.class, Object.class, (exchange, body) -> {
                            if (body instanceof Map) {
                                Map<String, Object> bodyMap = (Map<String, Object>) body;
                                // Validate empleado
                                ResponseEntity<Object> empleadoResponse = restTemplate.getForEntity(
                                        "http://localhost:8082/api/empleados/" + bodyMap.get("idEmpleado"),
                                        Object.class
                                );
                                if (!empleadoResponse.getStatusCode().is2xxSuccessful()) {
                                    throw new RuntimeException("Empleado not found");
                                }

                                // Validate interesado
                                ResponseEntity<Object> interesadoResponse = restTemplate.getForEntity(
                                        "http://localhost:8082/api/interesados/" + bodyMap.get("idInteresado"),
                                        Object.class
                                );
                                if (!interesadoResponse.getStatusCode().is2xxSuccessful()) {
                                    throw new RuntimeException("Interesado not found");
                                }
                            }
                            return Mono.just(body);
                        }))
                        .uri("http://localhost:8085"))
                .route("get-pruebas-service", r -> r
                        .path("/api/pruebas")
                        .and()
                        .method("GET")
                        .uri("http://localhost:8085"))
                .route("pruebas-en-curso-service", r -> r
                        .path("/api/pruebas/en-curso/**")
                        .uri("http://localhost:8085"))
                .route("autos-service", r -> r
                        .path("/api/vehiculos/**")
                        .uri("http://localhost:8085"))
                .build();
    }


}
