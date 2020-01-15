package es.icp.logs.firebase;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;

import es.icp.logs.core.MyLog;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    static FirebaseStorage storage = FirebaseStorage.getInstance();
    static FirebaseAuth auth = FirebaseAuth.getInstance();

    Context context;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        String titulo = remoteMessage.getNotification().getTitle();
        String mensajeRecibido = remoteMessage.getNotification().getBody();

        MyLog.d("Titulo:" + titulo);
        MyLog.d("Mensaje recibido:" + mensajeRecibido);
        MyLog.d("Fichero:" + MyLog.nombreFicheroLog);

        switch (remoteMessage.getNotification().getTitle().toString().trim()) {

            case "GET_LOG":
                String carpetaAlmacenamiento = getApplicationContext().getFilesDir().getPath();
                //String f = carpetaAlmacenamiento + File.separator + "log_20200114.txt";
                String f = carpetaAlmacenamiento + File.separator + MyLog.nombreFicheroLog;
                uploadFile(getApplicationContext(), f);
                break;

            default:
                MyLog.c("Titulo:" + titulo);
                MyLog.c("Mensaje recibido:" + mensajeRecibido);
                break;
        }

    }


    private void uploadFile(Context context, String fichero) {
        MyLog.d("Subiendo fichero..." + fichero);
        if (auth == null) auth = FirebaseAuth.getInstance();
        if (storage == null) storage = FirebaseStorage.getInstance();
        File file = new File(fichero);
        auth.signInAnonymously();
        StorageReference storageReference = storage.getReference();
        StorageReference ficheroReference = storageReference.child(file.getName());
        byte[] data = GetDataFile(context, fichero);
        UploadTask uploadTask = ficheroReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                MyLog.e("Fallo en storage: " + e.getMessage());
                Log.d("STORAGE", "Fallo en storage: " + e.getMessage());
                e.printStackTrace();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("STORAGE", "Fichero subido correctamente");
                MyLog.v("Fichero subido correctamente");
            }
        });

    }



    private static byte[] GetDataFile(Context context, String f) {
        ///data/data/es.icp.orangepistola/files/
        File file = new File(f);


        byte[] data = new byte[(int) file.length()];
        try {
            FileInputStream fin = new FileInputStream(new File(f));
            fin.read(data, 0, data.length);
            fin.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return data;
    }


}