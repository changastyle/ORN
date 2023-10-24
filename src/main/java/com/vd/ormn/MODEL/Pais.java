package com.vd.ormn.MODEL;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "paises")
@Data @NoArgsConstructor @AllArgsConstructor
public class Pais
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    @OneToOne() @JoinColumn(name = "fkInstalacion")
    private Instalacion instalacion;
    @OneToMany(mappedBy = "pais1")
    private List<Provincia> arrProvs;
    private LocalDate fecha;
    private LocalDateTime fechaHora;
    private boolean activo;
}
