package com.vd.ormn;

import com.vd.ormn.util.MasterUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    public MetodoORM(String nombreMetodo, String retType, String retTypeFull, boolean soyLista , String nombreClaseDeLaLista)
    {
        // CONSTRUCTOR PARA TIPO DE DATO LISTA:
        this.nombreMetodo = nombreMetodo;
        this.retType = retType;
        this.retTypeFull = retTypeFull;
        this.nombreFKSiSoyHijo = "";

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
        String sql = "";

        return sql;
    }

    //    private String nombre;
//    private String tipo;
//    private String clase;
//    private String nombreFK;
//    public boolean soyLista;
//    public boolean soyRelacion;
//    private ClaseORM claseDBPadre;
//    private String retType;
//    private String retTypeLista;

//    public String getSQL()
//    {
//        String rta  = "";
//        if (nombre.equalsIgnoreCase("id"))
//        {
//            rta = nombre + " INT PRIMARY KEY";
//            this.soyRelacion = false;
//        }
//        else if (clase.equalsIgnoreCase("int"))
//        {
//            rta = nombre + " INT";
//            this.soyRelacion = false;
//        }
//        else if(clase.equalsIgnoreCase("String"))
//        {
//            rta = nombre + " VARCHAR(255)";
//            this.soyRelacion = false;
//        }
//        else if(clase.equalsIgnoreCase("LocalDate"))
//        {
//            rta = nombre + " TIMESTAMP NOT NULL";
//            this.soyRelacion = false;
//        }
//        else if(clase.equalsIgnoreCase("LocalDateTime"))
//        {
//            rta = nombre + " TIMESTAMP NOT NULL";
//            this.soyRelacion = false;
//        }
//        else
//        {
////            String primeraLetra = nombre.
//            String nombrePMay = MasterUtil.capitalize(nombre);
//            this.soyRelacion = true;
//            this.nombreFK = "fk" + nombrePMay;
//            rta = "fk" + nombrePMay + " INT";
//        }
//
//        return rta;zz
//    }


//    public Class getClasePadreSiSoyHijo()
//    {
//        Class classPadre = null;
//
//        if(soyHijo)
//        {
//            try
//            {
//                classPadre = Class.forName(nombreClasePadre);
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//
//        return classPadre;
//    }
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
        String rtaLista = "METHODO : " + nombreMetodo + " | " + retType + "<" + tipoDeLaLista  + "> | soyLista : " + soyLista ;
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
