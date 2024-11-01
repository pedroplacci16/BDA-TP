package com.backend.autos_pruebas.models;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PATENTE")
    private String patente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_MODELO")
    private Modelo modelo;

    @Column(name = "ANIO")
    private Integer anio;
}
