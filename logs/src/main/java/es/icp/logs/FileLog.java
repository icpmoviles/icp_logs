package es.icp.logs;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLog {



    private static String CARPETA_LOG = "LOGS";



    /**
     * Escribe el log en un fichero de texto
     *
     * @param context
     * @param info
     */
    public  static void fw(Context context, InfoLog info, String nombreFichero) {

        try {
            //String carpetaAlmacenamiento = context.getExternalFilesDir(CARPETA_LOG).getPath();

            String carpetaAlmacenamiento = context.getFilesDir().getPath();

            //comprobamos si existe el directorio donde se almacenara el log, si no es asi lo creamos.
            File f = new File(carpetaAlmacenamiento);
            if (!f.isDirectory()) {
                f.mkdirs();
            }

          //String tiempo = Helper.dameMarcaTiempo("yyyyMMdd");



            //FileOutputStream fos = new FileOutputStream(carpetaAlmacenamiento + File.separator + "log_"+tiempo+".txt", true);
            FileOutputStream fos = new FileOutputStream(carpetaAlmacenamiento + File.separator + nombreFichero, true);
            //FileOutputStream fos = context.openFileOutput(carpetaAlmacenamiento +"/log.txt", Context.MODE_APPEND);
            OutputStreamWriter fout = new OutputStreamWriter(fos);
            fout.write(info.toLog() + "\n");
            fout.close();
            //Log.d("AA" ,carpetaAlmacenamiento + "/log_"+tiempo+".txt");
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
