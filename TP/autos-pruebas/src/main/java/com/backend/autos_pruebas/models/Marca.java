package com.backend.autos_pruebas.models;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Marcas")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NOMBRE")
    private String nombre;
}
