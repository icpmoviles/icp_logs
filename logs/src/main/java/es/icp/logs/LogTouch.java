package es.icp.logs;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogTouch {

    private static String nombreActivity;


    public static void init(final Context context, View v) {
        nombreActivity = ((Activity) context).getLocalClassName();
        final ViewGroup viewgroup = (ViewGroup) v;

        for (int i = 0; i < viewgroup.getChildCount(); i++) {
            View v1 = viewgroup.getChildAt(i);
            if (v1 instanceof ViewGroup) init(context, v1);
            String partes[] = v1.getClass().getGenericSuperclass().toString().split("\\.");
            final String superClase = v1.getClass().getGenericSuperclass().toString();
            if (partes.length > 0) {
                final String tipo = partes[partes.length - 1];
                final View vista = ((Activity) context).findViewById(v1.getId());

                if (vista != null) {
                    final String nombre = context.getResources().getResourceEntryName(vista.getId());

                    if (tipo.equals("EditText")) {


                        ((EditText) vista).addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                //MyLog.i(" => " + nombre + ", Valor:" + s.toString());
                                //MyLog.i(nombreActivity + ", TOUCH:" + nombre + ", Clase:" + superClase+ " Texto: " + s.toString());
/*
                                long yourmilliseconds = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mmss");
                                String tiempo = sdf.format(new Date(yourmilliseconds));*/


                                String tiempo = Helper.dameMarcaTiempo("yyyy-MM-dd hh:mmss");



                                FileLog.fw(context, new InfoLog(InfoLog.TIPO.PULSACION, tiempo, nombreActivity, nombre, tipo, "", s.toString()), MyLog.nombreFicheroLog);

                            }
                        });


                    }

                    vista.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                long yourmilliseconds = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String tiempo = sdf.format(new Date(yourmilliseconds));

                                SimpleDateFormat mls = new SimpleDateFormat("ss.SSS");
                                String tiempoTranscurrido = mls.format(new Date(event.getEventTime() - event.getDownTime()));
                                FileLog.fw(context, new InfoLog(InfoLog.TIPO.PULSACION,tiempo, nombreActivity, nombre, tipo, tiempoTranscurrido, ""), MyLog.nombreFicheroLog);
                                //MyLog.d("Tocado " + nombre);
                            }
                            return false;
                        }
                    });

                }
            }
        }
    }
}
