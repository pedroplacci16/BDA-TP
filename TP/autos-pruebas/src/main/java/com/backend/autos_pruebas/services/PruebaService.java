package com.backend.autos_pruebas.services;

import com.backend.autos_pruebas.models.Prueba;
import com.backend.autos_pruebas.repositories.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PruebaService {
    @Autowired
    private PruebaRepository pruebaRepository;
    public List<Prueba> getPruebasEnCurso(Date momento) {
        return pruebaRepository.findPruebasEnCurso(momento);
    }






    public List<Prueba> getAllPruebas() {
            return pruebaRepository.findAll();
        }

    public Prueba finalizarPrueba(Integer id, String comentarios, Date fechaHoraFin) {
        Prueba prueba = pruebaRepository.findById(id.longValue())
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada"));

        if (fechaHoraFin.before(prueba.getFechaHoraInicio())) {
            throw new RuntimeException("La fecha de finalización no puede ser anterior a la fecha de inicio");
        }

        prueba.setComentarios(comentarios);
        prueba.setFechaHoraFin(fechaHoraFin);

        return pruebaRepository.save(prueba);
    }




    public Prueba registrarPrueba(Prueba prueba) {
        // Validate vehicle availability
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(prueba.getFechaHoraInicio());
        calStart.add(Calendar.HOUR, -1);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(prueba.getFechaHoraFin());
        calEnd.add(Calendar.HOUR, 1);

        // Check if Empleado is available
        boolean empleadoBusy = pruebaRepository.existsByIdEmpleadoAndFechaHoraInicioBetween(
                prueba.getIdEmpleado(),
                prueba.getFechaHoraInicio(),
                prueba.getFechaHoraFin()
        );
        if (empleadoBusy) {
            throw new RuntimeException("Empleado has another test drive scheduled at this time");
        }

        // Check if Interesado is available
        boolean interesadoBusy = pruebaRepository.existsByIdInteresadoAndFechaHoraInicioBetween(
                prueba.getIdInteresado(),
                prueba.getFechaHoraInicio(),
                prueba.getFechaHoraFin()
        );
        if (interesadoBusy) {
            throw new RuntimeException("Interesado has another test drive scheduled at this time");
        }

        boolean isVehiculeInUse = pruebaRepository.existsByVehiculoAndFechaHoraInicioBetween(
                prueba.getVehiculo(),
                calStart.getTime(),
                calEnd.getTime()
        );



        if (isVehiculeInUse) {
            throw new RuntimeException("Vehículo no disponible en ese horario");
        }

        return pruebaRepository.save(prueba);
    }
}

