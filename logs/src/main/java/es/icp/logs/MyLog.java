package es.icp.logs;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;


public class MyLog {

    private static Boolean DEBUG = true;

    /**
     * Etiqueta a mostrar en el log
     */
    private static String TAG             = "DEBUG_ICP";
    private static int    TABULACION_JSON = 4;

    //variables para configurar lo que queremos mostrar en el debug
    private static Boolean mostrarFichero = false;
    private static Boolean mostrarClase   = true;
    private static Boolean mostrarMetodo  = true;
    private static Boolean mostrarLineas  = true;


    public static Boolean getDEBUG (){ return DEBUG; }

    public static void setDEBUG (Boolean DEBUG){ MyLog.DEBUG = DEBUG; }

    public static String getTAG (){ return TAG; }

    public static void setTAG (String TAG){ MyLog.TAG = TAG; }

    public static int getTabulacionJson (){ return TABULACION_JSON; }

    public static void setTabulacionJson (int tabulacionJson){ TABULACION_JSON = tabulacionJson; }

    public static Boolean getMostrarFichero (){ return mostrarFichero; }

    public static void setMostrarFichero (Boolean mostrarFichero){ MyLog.mostrarFichero = mostrarFichero; }

    public static Boolean getMostrarClase (){ return mostrarClase; }

    public static void setMostrarClase (Boolean mostrarClase){ MyLog.mostrarClase = mostrarClase; }

    public static Boolean getMostrarMetodo (){ return mostrarMetodo; }

    public static void setMostrarMetodo (Boolean mostrarMetodo){ MyLog.mostrarMetodo = mostrarMetodo; }

    public static Boolean getMostrarLineas (){ return mostrarLineas; }

    public static void setMostrarLineas (Boolean mostrarLineas){ MyLog.mostrarLineas = mostrarLineas; }

    /**
     * Mensaje de DEBUG
     *
     * @param texto Texto a mostrar
     */
    public static void d (Object texto){ if (DEBUG) Log.d(TAG, construirTexto(texto.toString(), 5)); }

    /**
     * Mensaje de ERROR
     *
     * @param texto Texto a mostrar
     */
    public static void e (Object texto){ if (DEBUG) Log.e(TAG, construirTexto(texto.toString(), 5)); }

    /**
     * Mensaje de WARNING
     *
     * @param texto Texto a mostrar
     */
    public static void w (Object texto){ if (DEBUG) Log.w(TAG, construirTexto(texto.toString(), 5)); }

    /**
     * Mensaje de INFORMATION
     *
     * @param texto Texto a mostrar
     */
    public static void i (Object texto){ if (DEBUG) Log.i(TAG, construirTexto(texto.toString(), 5)); }

    /**
     * Mensaje de VERBOSE
     *
     * @param texto Texto a mostrar
     */
    public static void v (Object texto){ if (DEBUG) Log.v(TAG, construirTexto(texto.toString(), 5)); }

    /**
     * Muestra de forma tabulada un objeto JSON
     *
     * @param jsonObject Objeto JSON
     */
    public static void json (JSONObject jsonObject){
        if (!DEBUG) return;
        try {
            l();
            Log.d(TAG, dameInfoTrace(4) + jsonObject.toString(TABULACION_JSON));
            l();
        } catch (Exception ex) {
            e(ex.getMessage());
        }
    }

    /**
     * Muestra de forma tabulada un array JSON
     *
     * @param jsonArray
     */
    public static void json (JSONArray jsonArray){
        if (!DEBUG) return;
        try {
            l();

            Log.d(TAG, dameInfoTrace(4) + jsonArray.toString(TABULACION_JSON));
            l();
        } catch (Exception ex) {
            e(ex.getMessage());
        }

    }

    //Generamos la estructura del texto que concatenamos antes para saber de donde estamos mostrando el error
    private static String construirTexto (String texto, int trace){
        String cadena = dameInfoTrace(5);
        cadena += ": " + texto;
        return cadena;
    }

    private static String dameInfoTrace (int elementoStackTrace){

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
        return cadena;
    }


    /**
     * Realiza la traza de una ejecución retrocediendo tantos puntos de ejecución como los indicados
     *
     * @param p Puntos de ejecución a retroceder
     */
    public static void trace (int p){
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        for (int i = 0; i < (p); i++) {
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

    /**
     * Obtiene la traza de ejecucion en la posicion indicada
     *
     * @param p Indice de traza a mostrar
     */
    public static String getTrace (int p){

        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = st[p];
        String cadena = "";
        if (mostrarFichero) cadena += stackTraceElement.getFileName() + ", ";
        if (mostrarClase) {
            String[] partes = stackTraceElement.getClassName().split("\\.");
            cadena += "{Class: " + partes[partes.length - 1] + "}";
        }
        cadena += "[Mthd: " + stackTraceElement.getMethodName() + "]";
        cadena += "[" + stackTraceElement.getLineNumber() + "]";
        return cadena;


    }


    private static String repeat (String texto, Integer n){
        StringBuilder buf = new StringBuilder(texto.length() * n);
        while (n-- > 0) {
            buf.append(texto);
        }
        return buf.toString();

    }

    /**
     * Dibuja un cuadro de texto de una sola linea
     * ┌---------------------------┐
     * |***  Lorem Dolor Ipsum  ***|
     * └---------------------------┘
     *
     * @param texto Texto a mostrar
     */
    public static void f (String texto){

        int longitud = 5;
        int trace = 6;

        if (DEBUG)
            Log.d(TAG, construirTexto("┌" + repeat("-", texto.length() + (longitud * 2)) + "┐", trace));
        if (DEBUG)
            Log.v(TAG, construirTexto("|" + repeat("*", 3) + "  " + texto + "  " + repeat("*", 3) + "|", trace));
        if (DEBUG)
            Log.d(TAG, construirTexto("└" + repeat("-", texto.length() + (longitud * 2)) + "┘", trace));
    }

    /**
     * Dibuja un cuadro de texto de una sola linea
     * ╔═══════════════════════════╗
     * ║     Lorem Dolor Ipsum     ║
     * ╚═══════════════════════════╝
     *
     * @param texto Texto a mostrar
     */
    public static void c (String texto){

        int longitud = 5;

        int trace = 6;

        if (DEBUG)
            Log.d(TAG, construirTexto("╔" + repeat("═", texto.length() + (longitud * 2)) + "╗", trace));
        if (DEBUG)
            Log.v(TAG, construirTexto("║" + repeat(" ", 5) + texto + repeat(" ", 5) + "║", trace));
        if (DEBUG)
            Log.d(TAG, construirTexto("╚" + repeat("═", texto.length() + (longitud * 2)) + "╝", trace));

    }

    /**
     * Dibuja una linea de separacion de log
     * ej: ----------------------------------------------------------------------------------------------------
     */
    public static void l (){
        i(repeat("-", 100));
    }

}



