package es.icp.logs;

import android.os.Build;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class MyException {

    private static final boolean SHOW_ERROR_LOG    = true;
    private static final boolean PRINT_STACK_TRACE = true;

    public static void e (Exception info, Object... params){

        try {
            if (MyException.SHOW_ERROR_LOG   ) MyLog.e(info.getMessage());
            if (MyException.PRINT_STACK_TRACE) info.printStackTrace();

            int elementoStackTrace = 3;
            StackTraceElement[] st = Thread.currentThread().getStackTrace();
            StackTraceElement stackTraceElement = st[elementoStackTrace];

            Field[] fields = Build.VERSION_CODES.class.getFields();
            String  osName = fields[Build.VERSION.SDK_INT + 1].getName();

            StringBuilder parametros = new StringBuilder();
            for (Object obj : params) {
                try {
                    parametros.append(obj.toString()).append(", ");
                } catch (Exception ex) {

                }
            }

            JSONObject json = new JSONObject();
            json.put("ORIGEN", "APK");

           /* json.put("IMEI",  Helper.getIMEI(  GlobalVariables.CONTEXT));
            json.put("MODELO", Helper.getModeloDispositivo( GlobalVariables.CONTEXT));*/

            json.put("SO", " SDK:" + Build.VERSION.SDK_INT + " VERSION: " + osName + " (" + Build.VERSION.RELEASE + ")");
            json.put("VERSION_CODE", BuildConfig.VERSION_CODE);
            json.put("EXCEPCION", info.toString());
            json.put("VALOR", info.getMessage());
            json.put("FICHERO", stackTraceElement.getFileName());
            json.put("METODO", stackTraceElement.getMethodName());
            json.put("LINEA", stackTraceElement.getLineNumber());
            json.put("PARAMETROS", parametros);

            MyLog.json(json);
            MyLog.e(info.getMessage());

        } catch (Exception ex2) {

        }

    }

}
