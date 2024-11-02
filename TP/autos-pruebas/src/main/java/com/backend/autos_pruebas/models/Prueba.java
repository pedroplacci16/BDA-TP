package com.backend.autos_pruebas.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Entity
@Table(name = "Pruebas")
public class Prueba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_VEHICULO")
    private Vehiculo vehiculo;

    @Column(name = "ID_INTERESADO")
    private Integer idInteresado;

    @Column(name = "ID_EMPLEADO")
    private Integer idEmpleado;

    @Column(name = "FECHA_HORA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date fechaHoraInicio;

    @Column(name = "FECHA_HORA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date fechaHoraFin;


    @Column(name = "COMENTARIOS")
    private String comentarios;
}

