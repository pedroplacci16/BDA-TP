package com.backend.autos_pruebas.dtos;
import lombok.Data;

@Data
public class RestrictedZone {
    private Coordinate noroeste;
    private Coordinate sureste;

    @Data
    public static class Coordinate {
        private double lat;
        private double lon;
    }
}
