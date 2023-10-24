package com.vd.ormn;


import com.vd.ormn.util.MasterUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import  java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ClaseORM
{
    boolean verbose = false;
    private Class claseReflection;
    private String nombreSimple;
    private String nombrePaquete;
    private String nombreTabla;
    private List<MetodoORM> arrMethodos;
    private List<Annotation> arrAnnotations;

    public ClaseORM(String nombreClaseRAW)
    {
        Class claseReflection = null;
        try
        {
            // 1 - TENGO UN NOMBRE LARGO TIPO : C:\Users\ngrossi\Desktop\ORMN\.\src\main\java\com\vd\ormn\MODEL\Provincia.java
            String nombrePaquete = MasterUtil.substring(nombreClaseRAW, "java\\");
//            System.out.println("NOMBRE_CLASE_RECIBIDO:" + nombreClaseNecesario);

            // 2 - QUITO  LO QUE ESTA ANTES DE JAVA// Y LUEGO QUITO LOS SLASHS (/) POR PUNTOS:
            nombrePaquete = MasterUtil.reemplazarAll(nombrePaquete, String.valueOf("\\\\"),".");

            // 3 - QUITO EL .JAVA DEL FINAL:
            nombrePaquete = MasterUtil.substring(nombrePaquete, "",".java" , false);

            // 4 - CREO LA CLASE REFLECTION Y GUARDO EL NOMBRE SIMPLE Y EL NOMBRE DE PAQUETE:
            String nombreClase = MasterUtil.substring(nombreClaseRAW,"\\", ".", false);
            this.nombreSimple = nombreClase;
            this.nombrePaquete = nombrePaquete;
            this.claseReflection = Class.forName(nombrePaquete);


            // 5 - UNA VEZ QUE TENGO LA CLASE PROCESO LAS ANOTATIONS:
            this.arrAnnotations = calcularAnotations();

            // 6 - CALCULAR LOS METHODOS GET:
            this.getAttrBasedOnGetters();

        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

    }

    public String toString()
    {
        String rta = "\n";

        rta += "    NOMBRE SIMPLE : " + nombreSimple + ",\n";
        rta += "    NOMBRE PAQUETE : " + nombrePaquete + ",\n";
        rta += "    NOMBRE TABLA : " + nombreTabla + ",\n";
        rta += "    METHODOS:\n";
        rta += "    [\n";

        for(MetodoORM metodoLoop: arrMethodos)
        {
            rta += "        " + metodoLoop.toString() + ",\n";
        }

        rta += "    ]\n";
        return rta;
    }


    public List<String> getConstraints()
    {
        List<String> arrConstraints = new ArrayList<>();
        List<MetodoORM> arrMethodsFK = arrMethodos.stream().filter(metodo -> metodo.isSoyHijo()).collect(Collectors.toList());

        String plantillaConstraint = "ALTER TABLE `" + nombreTabla +"` ADD FOREIGN KEY (`fkPais1`) REFERENCES `paises`(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;";
        String plantillaConstraintInicial = "ALTER TABLE `" + nombreTabla +"`";
        for(MetodoORM metodoRelLoop : arrMethodsFK)
        {
            String nombreTabla = metodoRelLoop.getNombreClasePadre();
            String constraintLoop = plantillaConstraintInicial + " ADD FOREIGN KEY (`" + metodoRelLoop.getNombreFKSiSoyHijo() + "`) REFERENCES `" + nombreTabla +"`(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;";
//            arrConstraints.add(constraintLoop);
        }

        return  arrConstraints;
    }



    private List<Annotation> calcularAnotations()
    {
        Annotation[] declaredAnnotations = claseReflection.getDeclaredAnnotations();
        List<Annotation> arrAnotations = Arrays.asList(declaredAnnotations);
//            System.out.println("ANNOTATIONS: "+ declaredAnnotations.length);
        for(Annotation annotationLoop: arrAnotations)
        {
//                System.out.println(annotationLoop);
            String realAnotation = annotationLoop.toString();
            if(realAnotation.contains("@javax.persistence.Table"))
            {
                int posName = realAnotation.indexOf("name=");
                String aux = realAnotation.substring(posName, realAnotation.length());
                int posSiguiente = aux.indexOf(",");
                aux = aux.substring(0,posSiguiente);


                int firstQuoute = aux.indexOf("\"");
                int lastQoute = aux.lastIndexOf("\"");
                this.nombreTabla = aux.substring(firstQuoute+1, lastQoute);

                if(verbose)
                {
                    System.out.println("------------------------");
                    System.out.println("|   TABLA : " + nombreTabla);
                    System.out.println("------------------------");
                }
            }
        }
        return arrAnotations;
    }
    private List<Field> procesarFieldsMagic()
    {
        List<Field> arrFieds = Arrays.asList(claseReflection.getDeclaredFields());
        List<Method> arrMethodsReflect = Arrays.asList(claseReflection.getDeclaredMethods());

        System.out.println("|----------- CAMPOS: --------------|");
        for (Field fieldLoop : arrFieds)
        {
            String fieldName = fieldLoop.getName();
            String parseoTipo = ORN.casteoTiposJava(fieldLoop.getType().toString());
            System.out.println(fieldName +" - [" + parseoTipo +"]");

            if(parseoTipo.equalsIgnoreCase("List"))
            {
                System.out.println("ACA ESTÄ EL BARDO: " + parseoTipo);

                String fieldNameCap = MasterUtil.capitalize(fieldName);
                List<Method> arrMethodosReflection = Arrays.stream(this.claseReflection.getDeclaredMethods()).collect(Collectors.toList());
                List<Method> arrMethodosFiltrados = arrMethodosReflection.stream().filter(method -> method.getName().equalsIgnoreCase("set" + fieldNameCap)).collect(Collectors.toList());

                if(arrMethodosFiltrados.size() > 0)
                {
                    Method setterList = arrMethodosFiltrados.get(0);
                    List<Parameter> arrParameters = Arrays.stream(setterList.getParameters()).collect(Collectors.toList());

                    System.out.println("PARAMETERS LIST (" + arrParameters.size() + "):");
                    for(Parameter parameterLoop : arrParameters)
                    {
//                            java.util.List<com.vd.ormn.MODEL.Provincia> arg0
                        String nombreClaseDeLista = MasterUtil.substring(parameterLoop.toString(), ".", ">" , verbose);
                        System.out.println(parameterLoop + " -> " + nombreClaseDeLista);
                    }
                }
            }
        }

        return arrFieds;
    }

    public List<MetodoORM> getAttrBasedOnGetters()
    {
        List<MetodoORM> arrRtaSorted = new ArrayList<>();
        List<MetodoORM> arrRta = new ArrayList<>();
        List<Method> arrMethods = Arrays.asList(claseReflection.getDeclaredMethods());

        // 1 - RECORRO TODOS LOS METHODOS GET:
        for (Method methodLoop : arrMethods)
        {
            // 2 - TRAIGO EL NOMBRE Y LO QUE RETORNA:
            String nombreMethodo = methodLoop.getName();
            String retTypeFull = methodLoop.getReturnType().toString();

            if(nombreMethodo.startsWith("get"))
            {
                // 3 - CONVIRTIENDO getNombre -> nombre:
                String nombreATT = MasterUtil.antiCapitalize(nombreMethodo.substring(3, nombreMethodo.length()));
//                nombreATT = MasterUtil.capitalize(nombreMethodo);

                // 4 - SI EL RETORNO ES DE TIPO LISTA, TENGO QUE AVERIGUAR EL SET DE TQUE TIPO ES SU ARGUMENTO 0:
                String retType = MasterUtil.substring(retTypeFull,".");
//                System.out.println("GETTER : " + nombreATT + " -> " + retType);

                String retMethod = methodLoop.getDeclaringClass().toString();

                // 5 - QUILOMBO PARA ENCONTRAR EL TIPO DE ARGS 0 SI ES UNA LISTA:
                if(retType.equalsIgnoreCase("List"))
                {
                    String nombreAttCap = MasterUtil.capitalize(nombreATT);
                    String tipoDeLaLista = dameElTipoDeLista(nombreAttCap);
                    MetodoORM metodoLoop = new MetodoORM(nombreATT,retType, retTypeFull,true, tipoDeLaLista);
                    arrRta.add(metodoLoop);
                }
                else if(esUnTipoDatoPrimitivo(retType))
                {
                    MetodoORM metodoLoop = new MetodoORM(nombreATT,retType);
                    arrRta.add(metodoLoop);
                }
                else
                {
                    String fkSiSoyHijo = dameFKDelMethodoSegunAnnotation(nombreATT);
                    MetodoORM metodoLoop = new MetodoORM(nombreATT,retType, retTypeFull,retType,fkSiSoyHijo);
                    arrRta.add(metodoLoop);
                }
            }
        }

        // LE DOY ORDEN A LA COSA:
        List<Field> arrFieds = Arrays.asList(claseReflection.getDeclaredFields());

        for (Field fieldLoop : arrFieds)
        {
            //String parseoTipo = ORN.casteoTiposJava(fieldLoop.getType().toString());
//            System.out.println(fieldLoop.getName());

            for(MetodoORM metodoLoop : arrRta)
            {
                if(metodoLoop.getNombreMetodo().equalsIgnoreCase(fieldLoop.getName()))
                {
                    arrRtaSorted.add(metodoLoop);
                }
            }
        }

        this.arrMethodos = arrRtaSorted;
        return arrRtaSorted;
    }
    private boolean esUnTipoDatoPrimitivo(String tipoDato)
    {
        boolean ok = false;

        if
        (
                tipoDato.equalsIgnoreCase("int") ||
                tipoDato.equalsIgnoreCase("String") ||
                tipoDato.equalsIgnoreCase("LocalDate") ||
                tipoDato.equalsIgnoreCase("LocalDateTime") ||
                tipoDato.equalsIgnoreCase("float") ||
                tipoDato.equalsIgnoreCase("double") ||
                tipoDato.equalsIgnoreCase("boolean")
        )
        {
            ok = true;
        }

        return ok;
    }
    private String dameElTipoDeLista(String nombreAttCap)
    {
        String rta = "";

        List<Method> arrMethodosReflection = Arrays.stream(this.claseReflection.getDeclaredMethods()).collect(Collectors.toList());
        List<Method> arrMethodosFiltrados = arrMethodosReflection.stream().filter(method -> method.getName().equalsIgnoreCase("set" + nombreAttCap)).collect(Collectors.toList());

        if(arrMethodosFiltrados.size() > 0)
        {
            Method setterList = arrMethodosFiltrados.get(0);
            List<Parameter> arrParameters = Arrays.stream(setterList.getParameters()).collect(Collectors.toList());

//            System.out.println("PARAMETERS LIST (" + arrParameters.size() + "):");
            for(Parameter parameterLoop : arrParameters)
            {
//                            java.util.List<com.vd.ormn.MODEL.Provincia> arg0
                String nombreClaseDeLista = MasterUtil.substring(parameterLoop.toString(), ".", ">" , verbose);
//                System.out.println(parameterLoop + " -> " + nombreClaseDeLista);

                rta = nombreClaseDeLista;
            }
//                        System.out.println("SETTER (" + parameterLoop +"): ");
        }

        return rta;
    }
    private String dameFKDelMethodoSegunAnnotation(String attBuscado)
    {
        String rta = "";

        List<Field> arrFields = Arrays.stream(claseReflection.getDeclaredFields()).collect(Collectors.toList());
        for(Field fieldLoop : arrFields)
        {
            if(fieldLoop.getName().equalsIgnoreCase(attBuscado))
            {
                List<Annotation> arrAnnotations = Arrays.asList(fieldLoop.getDeclaredAnnotations());
                for(Annotation anoLoop : arrAnnotations)
                {
                    String valorFK = MasterUtil.substringBetween(anoLoop.toString(),"name=\"",",");
                    rta = valorFK;
//                    System.out.println("ANO(" + valorFK +"):" );
//                    System.out.println("ANO(" + valorFK +"):" + anoLoop);
                }

//                System.out.println(fieldLoop);
            }
        }
        return rta;
    }
}
