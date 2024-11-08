package com.backend.autos_pruebas.dtos;

import lombok.Data;
import java.util.List;

@Data
public class AgencyConfig {
    private RestrictedZone.Coordinate coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<RestrictedZone> zonasRestringidas;
}

