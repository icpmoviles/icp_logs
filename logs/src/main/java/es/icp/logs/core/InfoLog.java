package es.icp.logs.core;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoLog {

    public enum TIPO {TEXTO, PULSACION}

    TIPO tipo;
    String tiempo;
    String activity;
    String nombre;
    String clase;
    String duracion;
    String extra;

    public InfoLog(TIPO tipo, String tiempo, String activity, String nombre, String clase, String duracion, String extra) {
        this.tipo = tipo;
        this.tiempo = tiempo;
        this.activity = activity;
        this.nombre = nombre;
        this.clase = clase;
        this.duracion = duracion;
        this.extra = extra;
    }

    public InfoLog(String cadena) {
        this.tipo = TIPO.TEXTO;
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.tiempo = sdf.format(new Date(yourmilliseconds));

        this.extra = cadena;
    }

    @Override
    public String toString() {
        return "Info{" +
                "tiempo='" + tiempo + '\'' +
                ", activity='" + activity + '\'' +
                ", nombre='" + nombre + '\'' +
                ", clase='" + clase + '\'' +
                ", duracion='" + duracion + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }


    public String toLog() {

        switch (tipo) {
            case TEXTO:
                return tiempo + " => " +extra;


            case PULSACION:
                return tiempo + " => " +
                        "TOC/ {"+ nombre +"}"+
                        " activity='" + activity + '\'' +
                        ", nombre='" + nombre + '\'' +
                        ", clase='" + clase + '\'' +
                        ", duracion='" + duracion + '\'' +
                        ", extra='" + extra + '\'';
            default:
                return "DESCONOCIDO";
        }

    }

}
