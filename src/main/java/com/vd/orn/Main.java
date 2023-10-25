package com.vd.orn;

import com.vd.orn.util.MasterUtil;

import java.io.File;
import java.util.List;

public class Main
{
    public static boolean verbose = false;
    public static void main( String[] args ) throws ClassNotFoundException
    {
        boolean verbose = Boolean.parseBoolean(MasterUtil.readENV("verbose"));

        System.out.println( "| ------------ ORM NICO(" + verbose +"): -----------|" );


        // 1 - LEER LA CARPETA:
        String modeloFolder = ".\\src\\main\\java\\com\\vd\\orn\\MODEL";
        List<File> arrFiles = ORN.readFolder( modeloFolder, false);
        for(File fileLoop : arrFiles)
        {
            // 2 - POR CADA ARCHIVO LEO Y PROCESO:
            String nombreClaseRAW = fileLoop.getAbsolutePath();
            String nombreClase = MasterUtil.substring(nombreClaseRAW,"\\", ".", verbose );

            if(verbose)
            {
                System.out.println("|--------------------------");
                System.out.println("|    MODELO ENCONTRADO A PROCESAR (" + nombreClase +") -> " + fileLoop );
                System.out.println("|--------------------------");
            }

            // 3 - TRAIGO EL PACKETE DE LA CLASE:
            ClaseORM claseLoop = new ClaseORM(nombreClaseRAW);
            System.out.println(claseLoop);
            System.out.println("------------------------");
            ORN.crearConstraints(claseLoop);
            ORN.crearTabla(claseLoop);
        }


    }

}
