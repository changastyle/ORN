package com.vd.ormn;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ORN
{
    public static void crearConstraints(ClaseORM claseDB)
    {
        Connection connection = null;
        String jdbcUrl = "jdbc:mysql://localhost:3305/orn?serverTimezone=America/New_York";
        String username = "root";
        String password = "lupita";

        try
        {
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            Statement statement = connection.createStatement();

            List<String> arrConstraint = claseDB.getConstraints();

            for(String strLoop: arrConstraint)
            {
                System.out.println("CREATE CONSTRAINT: "+ strLoop);
//                statement.execute(strLoop);
            }
            System.out.println("CONSTRAINT created successfully.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void conectar()
    {
        String jdbcUrl = "jdbc:mysql://localhost:3306/orn";
        String usuario = "root";
        String contraseña = "lupita";

        try
        {
            // Cargar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión a la base de datos
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            if (conexion != null)
            {
                System.out.println("Conexión exitosa a la base de datos.");
                // Realiza aquí tus operaciones con la base de datos
                conexion.close();
            }
        }
        catch (ClassNotFoundException e)
        {
            System.err.println("Error al cargar el controlador JDBC: " + e.getMessage());
        }
        catch (SQLException e)
        {
            System.err.println("Error de conexión a la base de datos: " + e.getMessage());
        }
    }

    public static String casteoTiposJava(String tipo)
    {
        String rta = "";

        if(tipo.startsWith("class"))
        {
            String vAux[] = tipo.split("\\.");


//            System.out.println("TIPO: " + tipo);
//            System.out.println("V: " + vAux.length);
            rta = vAux[vAux.length - 1];
//            System.out.println("RTA: " + rta);
        }
        else if(tipo.contains("java.util.List"))
        {
            rta = "List";
        }
        else
        {
            rta = tipo;
        }

        return rta;
    }

//    public List<MetodoORM> getMethodosDeClaseFromGetters()
//    {
//        List<MetodoORM> arrRtaSorted = new ArrayList<>();
//        List<MetodoORM> arrRta = new ArrayList<>();
//        List<Method> arrMethods = Arrays.asList();
//
//        for (Method methodLoop : arrMethods)
//        {
//            String nombreMethodo = methodLoop.getName();
//            String retTypeFull = methodLoop.getReturnType().toString();
//
//
//            if(nombreMethodo.startsWith("get"))
//            {
//                // 1 - CONVIRTIENDO getNombre -> nombre:
//                String primerLetra = nombreMethodo.substring(3,4).toLowerCase() ;
//                String nombreMethodoProcesado = primerLetra + nombreMethodo.substring(4,nombreMethodo.length());
//
//                // 2 - CONVIRTIENDO: class com.vd.ormn.MODEL.Pais -> Pais
//                int lastPuntoRetType = retTypeFull.lastIndexOf(".");
//                String retType = retTypeFull;
//                if(lastPuntoRetType > -1)
//                {
//                    retType = retTypeFull.substring(lastPuntoRetType + 1 , retTypeFull.length() );
//                }
////                String parseoTipo = ORN.casteoTiposJava(fieldLoop.getType().toString());
////                System.out.println("RET TYPE: " +nombreMethodo + " -> " + retType);
//                MetodoORM metodoLoop = MetodoORM.builder().
//                        nombre(nombreMethodoProcesado).
//                        tipo(methodLoop.getDeclaringClass().toString()).
//                        clase(retType).
//                        build();
//
//                arrRta.add(metodoLoop);
//            }
//        }
//
////        System.out.println("----------------------------");
//
//        // LE DOY ORDEN A LA COSA:
//        List<Field> arrFieds = Arrays.asList(clase.getDeclaredFields());
//
//        for (Field fieldLoop : arrFieds)
//        {
//            //String parseoTipo = ORN.casteoTiposJava(fieldLoop.getType().toString());
////            System.out.println(fieldLoop.getName());
//
//            for(MetodoORM metodoLoop : arrRta)
//            {
//                if(metodoLoop.getNombre().equalsIgnoreCase(fieldLoop.getName()))
//                {
//                    arrRtaSorted.add(metodoLoop);
//                }
//            }
//        }
//
//        return arrRtaSorted;
//    }
    public static List<File> readFolder( boolean verbose )
    {
        List<File> arrFiles = new ArrayList<>();

        String folderPath = ".\\src\\main\\java\\com\\vd\\ormn\\MODEL";

        // Convierte la ruta en un objeto Path
        Path folder = Paths.get(folderPath);

        if (Files.isDirectory(folder) && Files.exists(folder))
        {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder)) {
                for (Path file : directoryStream)
                {
//                    System.out.println("Nombre del archivo: " + file.getFileName());
                    arrFiles.add(file.toFile());
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("La carpeta no existe o no es una carpeta válida.");
        }

        return arrFiles;
    }


    public static void crearTabla(ClaseORM claseDB)
    {
        Connection connection = null;
        String jdbcUrl = "jdbc:mysql://localhost:3305/orn?serverTimezone=America/New_York";
        String username = "root";
        String password = "lupita";

        try
        {
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            String createTableSQL = "CREATE TABLE " + claseDB.getNombreTabla() + " (" ;

            for(MetodoORM metodoLoop : claseDB.getArrMethodos() )
            {
                createTableSQL += metodoLoop.getSQL() + ",";
            }

            createTableSQL = createTableSQL.substring(0 , createTableSQL.length() -1);
            createTableSQL += ")";

            Statement statement = connection.createStatement();

            System.out.println("CREATE TABLE: "+ createTableSQL);
            statement.execute(createTableSQL);
            System.out.println("Table created successfully.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String pasearJava2SQL(String str)
    {
        String rta = "";

        if(str.equalsIgnoreCase("int"))
        {
            rta = "INT";
        }
        else if(str.equalsIgnoreCase("String"))
        {
            rta = "TEXT()";
        }
        else
        {
            rta = "INT";
        }

        return rta;
    }

}
