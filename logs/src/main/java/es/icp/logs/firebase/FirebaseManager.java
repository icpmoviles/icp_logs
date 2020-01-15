package es.icp.logs.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import es.icp.logs.core.MyLog;

public class FirebaseManager {


    public static  void createToken(final ListenerFirebase listenerFirebase){

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //             Log.w(TAG, "getInstanceId failed", task.getException());
                            MyLog.c("Error a la generacion del token");
                            listenerFirebase.error(0, task);
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        MyLog.c("Token generado: "+ token);
                        listenerFirebase.ok(0, token);

                    }
                });
    }



}
