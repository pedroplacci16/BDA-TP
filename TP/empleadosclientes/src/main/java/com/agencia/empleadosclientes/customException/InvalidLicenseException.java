package com.agencia.empleadosclientes.customException;

public class InvalidLicenseException extends RuntimeException {
    public InvalidLicenseException(String message) {
        super(message);
    }
}
