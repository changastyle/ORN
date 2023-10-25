package com.vd.orn;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class MetodoORM
{

    private String nombreMetodo;
    private String retType;
    private String retTypeFull;
    private boolean soyLista;
    private boolean soyTipoDatoPrimitivo;
    private boolean soyHijo;
    private String nombreClasePadre;
    private String nombreFKSiSoyHijo;
    private String tipoDeLaLista;

    public MetodoORM(String nombreMetodo, String retType)
    {
        // CONSTRUCTOR PARA TIPO DE DATO PRIMITIVO:
        this.nombreMetodo = nombreMetodo;
        this.retType = retType;
        this.retTypeFull = retTypeFull;
        this.nombreClasePadre = nombreClasePadre;
        this.soyTipoDatoPrimitivo = true;
        this.soyHijo = false;
        this.nombreFKSiSoyHijo = "";
        this.soyLista = false;
        this.tipoDeLaLista = "";
    }
    public MetodoORM(String nombreMetodo, String retType, String retTypeFull, String nombreClasePadre , String nombreFKSiSoyHijo)
    {
        // CONSTRUCTOR PARA TIPO HIJO:
        this.nombreMetodo = nombreMetodo;
        this.retType = retType;
        this.retTypeFull = retTypeFull;
        this.nombreClasePadre = nombreClasePadre;
        this.soyHijo = true;
        this.nombreFKSiSoyHijo = nombreFKSiSoyHijo;
        this.soyTipoDatoPrimitivo = false;
        this.soyLista = false;
        this.tipoDeLaLista = "";
    }
    public MetodoORM(String nombreMetodo, String retType, String retTypeFull, boolean soyLista , String nombreClaseDeLaLista , String nombreFKSiSoyHijo)
    {
        // CONSTRUCTOR PARA TIPO DE DATO LISTA:
        this.nombreMetodo = nombreMetodo;
        this.retType = retType;
        this.retTypeFull = retTypeFull;
        this.nombreFKSiSoyHijo = nombreFKSiSoyHijo;

        if(soyLista)
        {
            this.soyLista = true;
            this.soyHijo = false;
            this.soyTipoDatoPrimitivo = false;
            this.tipoDeLaLista = nombreClaseDeLaLista;
            this.nombreClasePadre = "";
        }
    }

    public String getSQL()
    {
        String rta  = "";

        if(soyTipoDatoPrimitivo)
        {
            if(retType != null)
            {
                if (nombreMetodo.equalsIgnoreCase("id"))
                {
                    rta = nombreMetodo + " INT PRIMARY KEY";
                }
                else if (retType.equalsIgnoreCase("int"))
                {
                    rta = nombreMetodo + " INT";
                }
                else if(retType.equalsIgnoreCase("String"))
                {
                    rta = nombreMetodo + " VARCHAR(255)";
                }
                else if(retType.equalsIgnoreCase("LocalDate"))
                {
                    rta = nombreMetodo + " DATE NOT NULL";
                }
                else if(retType.equalsIgnoreCase("LocalDateTime"))
                {
                    rta = nombreMetodo + " DATETIME NOT NULL";
                }
            }
        }
        else if(soyLista)
        {
            rta = getNombreFKSiSoyHijo() + " INT";
        }
        else if(soyHijo)
        {
            rta = getNombreFKSiSoyHijo() + " INT";
        }

        return rta;
    }

    public String getNombreClasePadreSiSoyHijo()
    {
        String rta = "";

        if(soyHijo)
        {
            rta = nombreClasePadre;
        }

        return rta;
    }
    @Override
    public String toString()
    {
        String rta = "";
        String rtaLista = "METHODO : " + nombreMetodo + " | " + retType + "<" + tipoDeLaLista  + "> | soyLista : " + soyLista  + " | FK: " + getNombreFKSiSoyHijo();
        String rtaHijo ="METHODO : " + nombreMetodo + " | " + retType + " | soyHijo : " + soyHijo + " | nombreClasePadre: " + nombreClasePadre + " | FK:" + nombreFKSiSoyHijo;
        String rtaPrimitivo ="METHODO : " + nombreMetodo + " | " + retType;

        if(soyLista)
        {
            rta = rtaLista;
        }
        else if(soyHijo)
        {
            rta = rtaHijo;
        }
        else
        {
            rta =rtaPrimitivo;
        }

        return rta;
    }
}
