package com.backend.autos_pruebas.services;

import com.backend.autos_pruebas.models.Posicion;
import com.backend.autos_pruebas.repositories.PosicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;


@Service
public class PosicionService {
    @Autowired
    private PosicionRepository posicionRepository;

    @Autowired
    private RestrictedZoneService restrictedZoneService;

    @Transactional
    public Posicion savePosicion(Posicion posicion) {
        if (posicion.getFechaHora() == null) {
            posicion.setFechaHora(new Date());
        }

        if (restrictedZoneService.isPositionRestricted(posicion.getLatitud(), posicion.getLongitud())) {
            System.out.println("Posicion guardada pero prohibida");
        }

        return posicionRepository.save(posicion);
    }
}




