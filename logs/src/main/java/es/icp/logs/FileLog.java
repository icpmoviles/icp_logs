package es.icp.logs;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileLog {



    private static String CARPETA_LOG = "LOGS";



    /**
     * Escribe el log en un fichero de texto
     *
     * @param context
     * @param info
     */
    public  static void fw(Context context, InfoLog info) {



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
