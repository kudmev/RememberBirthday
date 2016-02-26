package dmitrykuznetsov.rememberbirthday;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Дмитрий on 23.07.2015.
 */
public class UpdateAlarmReceiverAfterReboot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Alarm", "UpdateAlarmReceiverAfterReboot");
//        Toast.makeText(context, "Запустилось обновление будильников",
//                Toast.LENGTH_SHORT).show();
        Intent serviceIntent=new Intent(context, ServiceUpdateAlarmAfterReboot.class);

        context.startService(serviceIntent);


    }
}
