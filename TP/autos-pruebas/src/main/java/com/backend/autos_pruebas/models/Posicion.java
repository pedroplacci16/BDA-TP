package com.backend.autos_pruebas.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "Posiciones")
public class Posicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_VEHICULO")
    private Vehiculo vehiculo;

    @Column(name = "FECHA_HORA")
    private Date fechaHora;

    @Column(name = "LATITUD")
    private Float latitud;

    @Column(name = "LONGITUD")
    private Float longitud;

}
