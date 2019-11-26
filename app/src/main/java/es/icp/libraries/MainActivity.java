package es.icp.libraries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import es.icp.logs.MyLog;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyLog.setDEBUG(true);
        MyLog.d("pruebas");

        MyLog.d("cambios 2 ");


        String json = "{\"data\":{\"VERSION_INT\":8,\"VERSION\":\"1.4.0\",\"RUTA_DESCARGA\":\"http:\\/\\/logistica3.icp.es\\/ICP_app_store\\/assets\\/apk\\/svdg\\/svdg.apk\",\"PACKAGE_NAME\":\"software.icp.com.sd_vdg\",\"ACTUALIZACION_CRITICA\":true}}";


        try {
            JSONObject jsonObject = new JSONObject(json);

            MyLog.json(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        MyLog.c("Lorem Dolor Ipsum");
        MyLog.f("Lorem Dolor Ipsum");




    }
}
