package es.icp.logs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.jaredrummler.android.device.DeviceName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    @SuppressLint ("MissingPermission")
    public static String getIMEI (Context ctx){
        TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static String getModeloDispositivo (Context ctx){
        try {
            String deviceName = DeviceName.getDeviceInfo(ctx).manufacturer + " " + DeviceName.getDeviceInfo(ctx).marketName;
            return deviceName;
        } catch (Exception ex) {
            MyException.e(ex);
            return "Desconocido";
        }

    }

    public static String dameMarcaTiempo(String formato){
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String tiempo = sdf.format(new Date(yourmilliseconds));
        return tiempo;
    }

}
