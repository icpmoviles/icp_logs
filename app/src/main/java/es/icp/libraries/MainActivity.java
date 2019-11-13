package es.icp.libraries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.icp.logs.MyLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyLog.d("pruebas");
    }
}
