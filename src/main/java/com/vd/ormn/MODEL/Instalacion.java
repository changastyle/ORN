package com.vd.ormn.MODEL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "instalaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instalacion
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
}
