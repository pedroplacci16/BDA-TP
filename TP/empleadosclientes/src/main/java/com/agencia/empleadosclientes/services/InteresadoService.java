package com.agencia.empleadosclientes.services;

import com.agencia.empleadosclientes.models.Interesado;
import com.agencia.empleadosclientes.repositories.InteresadoRepository;
import com.agencia.empleadosclientes.customException.InvalidLicenseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class InteresadoService {

    @Autowired
    private InteresadoRepository interesadoRepository;

    public List<Interesado> getAllInteresados() {
        return interesadoRepository.findAll();
    }

    public Interesado getInteresadoById(Long id) {
        return interesadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interesado not found with id: " + id));
    }

    public Interesado saveInteresado(Interesado interesado) {
        validateLicenseExpiration(interesado.getFechaVencimientoLicencia());
        return interesadoRepository.save(interesado);
    }

    public Interesado updateInteresado(Long id, Interesado interesadoDetails) {
        Interesado interesado = getInteresadoById(id);
        validateLicenseExpiration(interesadoDetails.getFechaVencimientoLicencia());

        interesado.setNombre(interesadoDetails.getNombre());
        interesado.setApellido(interesadoDetails.getApellido());
        interesado.setTipoDocumento(interesadoDetails.getTipoDocumento());
        interesado.setDocumento(interesadoDetails.getDocumento());
        interesado.setRestringido(interesadoDetails.getRestringido());
        interesado.setNroLicencia(interesadoDetails.getNroLicencia());
        interesado.setFechaVencimientoLicencia(interesadoDetails.getFechaVencimientoLicencia());

        return interesadoRepository.save(interesado);
    }

    public void deleteInteresado(Long id) {
        Interesado interesado = getInteresadoById(id);
        interesadoRepository.delete(interesado);
    }

    private void validateLicenseExpiration(Date licenseExpirationDate) {
        if (licenseExpirationDate == null) {
            throw new InvalidLicenseException("La fecha de vencimiento de la licencia es requerida");
        }

        Date currentDate = new Date();
        if (licenseExpirationDate.before(currentDate)) {
            throw new InvalidLicenseException("La licencia está vencida. No se puede registrar con una licencia vencida");
        }
    }
    public boolean canDrive(Long id) {
        Interesado interesado = getInteresadoById(id);

        if (interesado.getRestringido()) {
            return false;
        }

        Date currentDate = new Date();
        if (interesado.getFechaVencimientoLicencia() == null ||
                interesado.getFechaVencimientoLicencia().before(currentDate)) {
            return false;
        }

        return true;
    }

}
