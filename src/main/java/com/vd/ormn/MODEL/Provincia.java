package com.vd.ormn.MODEL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "provincias")
@Data @NoArgsConstructor @AllArgsConstructor
public class Provincia
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    @ManyToOne() @JoinColumn(name = "fkPais")
    private Pais pais1;
    @OneToMany(mappedBy = "ciudadX")
    private List<Ciudad> arrCiudades;
    private boolean activo;
}
