package com.vd.ormn;

import com.vd.ormn.util.MasterUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static boolean verbose = false;
    public static void main( String[] args ) throws ClassNotFoundException
    {
        boolean verbose = Boolean.parseBoolean(MasterUtil.readENV("verbose"));

        System.out.println( "| ------------ ORM NICO(" + verbose +"): -----------|" );


        // 1 - LEER LA CARPETA:
        List<File> arrFiles = ORN.readFolder(false);
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
//            nombreClaseNecesario = MasterUtil.reemplazarAll(nombreClaseNecesario, String.valueOf("\\\\"),".");
//            nombreClaseNecesario = MasterUtil.reemplazarAll(nombreClaseNecesario, ".java","");
//            Class claseReflection = Class.forName(nombreClaseNecesario);
//            ClaseORM clase = new ClaseORM(claseReflection,fin);

//            System.out.println("CLASE:"  + clase);

//            ORN.crearConstraints(clase);
            //            crearTabla(clase);
        }


    }

}
