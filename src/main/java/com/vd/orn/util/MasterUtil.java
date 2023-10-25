package com.vd.orn.util;

public class MasterUtil
{
    public static String capitalize(String str)
    {
        String rta = "";

        String primerLetra = String.valueOf(str.charAt(0));
        String resto = str.substring(1, str.length());

        rta = primerLetra.toUpperCase() + "" + resto;

        return rta;
    }
    public static String antiCapitalize(String str)
    {
        String rta = "";

        String primerLetra = String.valueOf(str.charAt(0));
        String resto = str.substring(1, str.length());

        rta = primerLetra.toLowerCase() + "" + resto;

        return rta;
    }
    public static String substring(String strRAW , String separador)
    {
        String rta = "";

        if(strRAW.contains(separador))
        {
            int posSeparador = strRAW.lastIndexOf(separador);
            int largoSeparador = separador.length();
            if(posSeparador > -1)
            {
                rta = strRAW.substring(posSeparador + largoSeparador, strRAW.length());
            }
        }
        else
        {
            rta = strRAW;
        }

        return rta;
    }
    public static String substring(String strRAW , String separador , String separadorFIN )
    {
        return substring(strRAW, separador,separadorFIN, false);
    }
//    public static String substringFirstIndexOf(String strRAW , String separador , String separadorFIN )
//    {
//        String aux = substring(strRAW, separador,separadorFIN, true);
//        String rta = aux
////        String rta = substring(aux , separadorFIN,"",true);
//        return aux;
//    }
    public static String substringBetween(String strRAW , String separadorInicial , String separadorFIN)
    {
        String rta = "";

        if(strRAW.contains(separadorInicial))
        {
//            System.out.println("ESTA (" + separadorInicial +")");
            int posInicial = strRAW.indexOf(separadorInicial);
            int largoSeparadorInicial = separadorInicial.length();
            int largoSeparadorFinal = separadorFIN.length();

            String sub = strRAW.substring(posInicial + largoSeparadorInicial , strRAW.length());
            if(sub.length() > 0)
            {
                int posFinal = sub.indexOf(separadorFIN);
                rta = sub.substring(0, posFinal - largoSeparadorFinal);
            }
        }




        return rta;
    }
    public static String substring(String strRAW , String separador , String separadorFIN, boolean firstIndexOF)
    {
        String rta = "";

        int posSeparador = -1;
        if(firstIndexOF)
        {
            posSeparador = strRAW.indexOf(separador);
        }
        else
        {
            posSeparador = strRAW.lastIndexOf(separador);
        }
        int posSeparadorFin = strRAW.lastIndexOf(separadorFIN);
        int largoSeparador = separador.length();

        boolean verbose = Boolean.parseBoolean(MasterUtil.readENV("verbose"));
        if(verbose)
        {
            System.out.println("| ----------- SEPARANDO  [" + strRAW +"]  POR " + separador + " Y  "+ separadorFIN + ") ------------------|");
            System.out.println("    POS LAST SEPARADOR (" + separador +"): " +posSeparador +  " | LARGO SEPARADOR : " + largoSeparador);
            System.out.println("    POS SEPARADOR FIN (" + separadorFIN +"): " + posSeparadorFin);
            System.out.println("    LARGO CADENA: " + strRAW.length());
        }

        if(separador.length() == 0)
        {
            rta = strRAW.substring(0, posSeparadorFin);
        }
        else
        {
            if(posSeparador > -1)
            {
                if(posSeparadorFin > -1)
                {
                    rta = strRAW.substring(posSeparador + largoSeparador, posSeparadorFin);
                }
                else
                {
                    rta = strRAW.substring(posSeparador + largoSeparador, largoSeparador);
                }
            }
        }


        if(verbose)
        {
            System.out.println("    |------------------------|");
            System.out.println("    |    RTA: " + rta);
            System.out.println("    |------------------------|");
        }



        return rta;
    }
    public static String reemplazarAll(String strRAW , String buscado, String nuevo , boolean verbose)
    {

        System.out.println("| ----------- REEMPLAZANDO  [" + strRAW +"]  POR " + buscado + " Y  "+ nuevo + ") ------------------|");

        return reemplazarAll(strRAW, buscado, nuevo);
    }
    public static String reemplazarAll(String strRAW , String buscado, String nuevo)
    {
        String rta = "";

        rta = strRAW.replaceAll(buscado,nuevo);

        return rta;
    }

    public static String readENV(String nombreVariable)
    {
        String valor = System.getenv(nombreVariable);

        if (valor != null)
        {
//            System.out.println("El valor de la variable " + nombreVariable + " es: " + valor);
        }
        else
        {
            valor = "-1";
//            System.out.println("La variable " + nombreVariable + " no está definida en el entorno.");
        }

        return valor;
    }
}
