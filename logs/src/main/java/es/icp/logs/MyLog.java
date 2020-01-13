package es.icp.logs;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyLog {

    enum TIPO {DEBUG, ERROR, WARNING, INFORMATION, VERBOSE, INFO}

   private static Boolean DEBUG = BuildConfig.DEBUG;

    /**
     * Etiqueta a mostrar en el log
     */
    private static String TAG = "DEBUG_ICP";
    private static int TABULACION_JSON = 4;

    //variables para configurar lo que queremos mostrar en el debug
    private static Boolean mostrarFichero = false;
    private static Boolean mostrarClase = true;
    private static Boolean mostrarMetodo = true;
    private static Boolean mostrarLineas = true;

    private static Boolean SAVE_FILE = true;
    private static String CARPETA_LOG = "LOGS";
    private static Context context;
    private static StackTraceElement traceElement; //vb para almacenar la traza cuando la mostamos en el log, para poder guardarla en el ficheo si queremos.


    public static Boolean getDEBUG() {
        return DEBUG;
    }

    public static void setDEBUG(Boolean DEBUG) {
        MyLog.DEBUG = DEBUG;
    }

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        MyLog.TAG = TAG;
    }

    public static int getTabulacionJson() {
        return TABULACION_JSON;
    }

    public static void setTabulacionJson(int tabulacionJson) {
        TABULACION_JSON = tabulacionJson;
    }

    public static Boolean getMostrarFichero() {
        return mostrarFichero;
    }

    public static void setMostrarFichero(Boolean mostrarFichero) {
        MyLog.mostrarFichero = mostrarFichero;
    }

    public static Boolean getMostrarClase() {
        return mostrarClase;
    }

    public static void setMostrarClase(Boolean mostrarClase) {
        MyLog.mostrarClase = mostrarClase;
    }

    public static Boolean getMostrarMetodo() {
        return mostrarMetodo;
    }

    public static void setMostrarMetodo(Boolean mostrarMetodo) {
        MyLog.mostrarMetodo = mostrarMetodo;
    }

    public static Boolean getMostrarLineas() {
        return mostrarLineas;
    }

    public static void setMostrarLineas(Boolean mostrarLineas) {
        MyLog.mostrarLineas = mostrarLineas;
    }

    public static void init(Context ctx) {
        context = ctx;
    }

    /**
     * Mensaje de DEBUG
     *
     * @param texto Texto a mostrar
     */
    public static void d(Object texto) {
        String cadena = construirTexto(texto.toString(), 5);
        if (DEBUG) Log.d(TAG, cadena);
        if (SAVE_FILE) saveFichero(TIPO.DEBUG, cadena);
    }

    /**
     * Mensaje de ERROR
     *
     * @param texto Texto a mostrar
     */
    public static void e(Object texto) {
        String cadena = construirTexto(texto.toString(), 5);
        if (DEBUG) Log.e(TAG, cadena);
        if (SAVE_FILE) saveFichero(TIPO.ERROR, cadena);
    }

    /**
     * Mensaje de WARNING
     *
     * @param texto Texto a mostrar
     */
    public static void w(Object texto) {
        String cadena = construirTexto(texto.toString(), 5);
        if (DEBUG) Log.w(TAG, cadena);
        if (SAVE_FILE) saveFichero(TIPO.WARNING, cadena);
    }

    /**
     * Mensaje de INFORMATION
     *
     * @param texto Texto a mostrar
     */
    public static void i(Object texto) {
        String cadena = construirTexto(texto.toString(), 5);
        if (DEBUG) Log.i(TAG, cadena);
        if (SAVE_FILE) saveFichero(TIPO.INFORMATION, cadena);
    }

    /**
     * Mensaje de VERBOSE
     *
     * @param texto Texto a mostrar
     */
    public static void v(Object texto) {
        String cadena = construirTexto(texto.toString(), 5);
        if (DEBUG) Log.v(TAG, cadena);
        if (SAVE_FILE) saveFichero(TIPO.VERBOSE, cadena);
    }

    /**
     * Muestra de forma tabulada un objeto JSON
     *
     * @param jsonObject Objeto JSON
     */
    public static void json(JSONObject jsonObject) {
        if (!DEBUG) return;
        try {
            l();
            String cadena = dameInfoTrace(4) + jsonObject.toString(TABULACION_JSON);
            fw(context, new InfoLog(cadena));
            Log.d(TAG, cadena);

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
    public static void json(JSONArray jsonArray) {
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
    private static String construirTexto(String texto, int trace) {
        String cadena = dameInfoTrace(5);
        cadena += ": " + texto;
        return cadena;
    }

    private static void saveFichero(TIPO tipo, String texto) {
        if (context != null) {
            long yourmilliseconds = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String tiempo = sdf.format(new Date(yourmilliseconds));

            texto = tipo.toString().substring(0,3) + "/ " + texto;
            fw(context, new InfoLog(texto));
        }
    }

    private static String dameInfoTrace(int elementoStackTrace) {

        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        String cadena = "";
        StackTraceElement stackTraceElement = st[elementoStackTrace];

        traceElement = stackTraceElement;

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
    public static void trace(int p) {
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
    public static String getTrace(int p) {

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


    private static String repeat(String texto, Integer n) {
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
    public static void f(String texto) {

        int longitud = 5;
        int trace = 6;

        String linea1 = construirTexto("┌" + repeat("-", texto.length() + (longitud * 2)) + "┐", trace);
        String linea2 =construirTexto("|" + repeat("*", 3) + "  " + texto + "  " + repeat("*", 3) + "|", trace);
        String linea3 = construirTexto("└" + repeat("-", texto.length() + (longitud * 2)) + "┘", trace);

        if (DEBUG) {
            Log.d(TAG, linea1);
            Log.v(TAG, linea2 );
            Log.d(TAG, linea3);
        }

        if (SAVE_FILE) {
            saveFichero(TIPO.VERBOSE, linea1);
            saveFichero(TIPO.VERBOSE, linea2);
            saveFichero(TIPO.VERBOSE, linea3);
        }
    }

    /**
     * Dibuja un cuadro de texto de una sola linea
     * ╔═══════════════════════════╗
     * ║     Lorem Dolor Ipsum     ║
     * ╚═══════════════════════════╝
     *
     * @param texto Texto a mostrar
     */
    public static void c(String texto) {

        int longitud = 5;
        int trace = 6;


        String linea1 = construirTexto("╔" + repeat("═", texto.length() + (longitud * 2)) + "╗", trace);
        String linea2 = construirTexto("║" + repeat(" ", 5) + texto + repeat(" ", 5) + "║", trace);
        String linea3 = construirTexto("╚" + repeat("═", texto.length() + (longitud * 2)) + "╝", trace);

        if (DEBUG) {
            Log.d(TAG, linea1);
            Log.v(TAG, linea2 );
            Log.d(TAG, linea3);
        }

        if (SAVE_FILE) {
            saveFichero(TIPO.VERBOSE, linea1);
            saveFichero(TIPO.VERBOSE, linea2);
            saveFichero(TIPO.VERBOSE, linea3);
        }

    }


    /**
     * Dibuja un cuadro de texto de una sola linea
     * ╔═══════════════════════════╗
     * ║     Lorem Dolor Ipsum     ║
     * ╚═══════════════════════════╝
     *
     * @param texto Texto a mostrar
     */
    public static void ce(String texto) {

        int longitud = 5;
        int trace = 6;




        String linea1 = construirTexto("╔" + repeat("═", texto.length() + (longitud * 2)) + "╗", trace);
        String linea2 = construirTexto("║" + repeat(" ", 5) + texto + repeat(" ", 5) + "║", trace);
        String linea3 = construirTexto("╚" + repeat("═", texto.length() + (longitud * 2)) + "╝", trace);

        if (DEBUG) {
            Log.d(TAG, linea1);
            Log.v(TAG, linea2 );
            Log.d(TAG, linea3);
        }

        if (SAVE_FILE) {
            saveFichero(TIPO.ERROR, linea1);
            saveFichero(TIPO.ERROR, linea2);
            saveFichero(TIPO.ERROR, linea3);
        }
    }


    /**
     * Dibuja una linea de separacion de log
     * ej: ----------------------------------------------------------------------------------------------------
     */
    public static void l() {
        i(repeat("-", 100));
    }


    /**
     * Escribe el log en un fichero de texto
     *
     * @param context
     * @param info
     */
    private static void fw(Context context, InfoLog info) {

        if (!SAVE_FILE) return; //si no esta habilitado el escribir en el fichero salimos del metodo

        try {
            String carpetaAlmacenamiento = context.getExternalFilesDir(CARPETA_LOG).getPath();

            //comprobamos si existe el directorio donde se almacenara el log, si no es asi lo creamos.
            File f = new File(carpetaAlmacenamiento);
            if (!f.isDirectory()) {
                f.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(carpetaAlmacenamiento + "/log.txt", true);
            //FileOutputStream fos = context.openFileOutput(carpetaAlmacenamiento +"/log.txt", Context.MODE_APPEND);
            OutputStreamWriter fout = new OutputStreamWriter(fos);
            fout.write(info.toLog() + "\n");
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}



