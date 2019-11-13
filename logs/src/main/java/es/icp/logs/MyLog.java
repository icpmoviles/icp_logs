package es.icp.logs;


import android.util.Log;



public class MyLog {

    private static Boolean DEBUG = true;

    //Tag para mostrar en el Log
    private static String TAG = "DEBUG_ICP";

    //variables para configurar lo que queremos mostrar en el debug
    private static Boolean mostrarFichero = false;
    private static Boolean mostrarClase   = true;
    private static Boolean mostrarMetodo  = true;
    private static Boolean mostrarLineas  = true;

    //Metodos de DEBUG / ERROR
    public static void d (Object texto){ if (DEBUG) Log.d(TAG, construirTexto(texto.toString())); }

    public static void e (Object texto){ if (DEBUG) Log.e(TAG, construirTexto(texto.toString())); }

    public static void w (Object texto){ if (DEBUG) Log.w(TAG, construirTexto(texto.toString())); }

    public static void i (Object texto){ if (DEBUG) Log.i(TAG, construirTexto(texto.toString())); }

    public static void v (Object texto){ if (DEBUG) Log.v(TAG, construirTexto(texto.toString())); }


    //Generamos la estructura del texto que concatenamos antes para saber de donde estamos mostrando el error
    private static String construirTexto (String texto){
        int elementoStackTrace = 4;
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        String cadena = "";
        StackTraceElement stackTraceElement = st[elementoStackTrace];
        if (mostrarFichero) cadena += stackTraceElement.getFileName() + ", ";
        if (mostrarClase) {
            String[] partes = stackTraceElement.getClassName().split("\\.");
            cadena += "{" + partes[partes.length - 1] + "}";
        }
        if (mostrarMetodo) cadena += "[" + stackTraceElement.getMethodName() + "]";
        if (mostrarLineas) cadena += "[" + stackTraceElement.getLineNumber() + "]";
        cadena += ": " + texto;
        return cadena;
    }


    public static void trace (int p ){

        StackTraceElement[] st = Thread.currentThread().getStackTrace();


        for (int i =0 ; i <(p); i++){

            StackTraceElement stackTraceElement = st[i];
            String cadena = String.valueOf(i) + ".-";
            if (mostrarFichero) cadena += stackTraceElement.getFileName() + ", ";
            if (mostrarClase) {
                String[] partes = stackTraceElement.getClassName().split("\\.");
                cadena += "{" + partes[partes.length - 1] + "}";
            }
            cadena += "[" + stackTraceElement.getMethodName() + "]";
            cadena += "[" + stackTraceElement.getLineNumber() + "]";
            MyLog.d(cadena);
        }

    }


    public static String getTrace (int p ){

        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = st[p];
        String cadena ="";
        if (mostrarFichero) cadena += stackTraceElement.getFileName() + ", ";
        if (mostrarClase) {
            String[] partes = stackTraceElement.getClassName().split("\\.");
            cadena += "{Class: " + partes[partes.length - 1] + "}";
        }
        cadena += "[Mthd: " + stackTraceElement.getMethodName() + "]";
        cadena += "[" + stackTraceElement.getLineNumber() + "]";
        return cadena;


    }


    public static String repeat (String texto, Integer n){
        StringBuilder buf = new StringBuilder(texto.length() * n);
        while (n-- > 0) {
            buf.append(texto);
        }
        return buf.toString();

    }

    //dibuja un cuadrado en la ventana del log.
    public static void f (String texto){

        int longitud = 5;
        //val s = 5
        String t = new String("-");
        d("┌" + repeat("-", texto.length() + (longitud * 2)) + "┐");
        v("|" + repeat("*", 3) +"  "+ texto+ "  " + repeat("*", 3) + "|");
        d("└" + repeat("-", texto.length() + (longitud * 2)) + "┘");

    }

    //dibuja un cuadrado en la ventana del log.
    public static void c(String texto){

        int longitud = 5;
        //val s = 5

        String t = new String("═");

        d("╔" + repeat("═", texto.length() + (longitud * 2)) + "╗");
        v("║" + repeat(" ", 5) + texto + repeat(" ", 5) + "║");
        d("╚" + repeat("═", texto.length() + (longitud * 2)) + "╝");

    }


    public static void l(){
        i(repeat("-", 100));
    }




}



