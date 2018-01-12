package dmitrykuznetsov.rememberbirthday.old;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import dmitrykuznetsov.rememberbirthday.common.service.notification.ServiceSendNotification;

/**
 * Created by Dmitry Kuznetsov on 23.07.2015.
 */
public class AlarmNotificationReceiver extends BroadcastReceiver {
    public static final String ACTION_SEND_NOTIFICATION = "dmitrykuznetsov.rememberbirthday.ACTION_SEND_NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("start", "AlarmNotificationReceiver");
        Intent serviceIntent = new Intent(context, ServiceSendNotification.class);
        int rowId = intent.getIntExtra("rowId", 0);
        long milliseconds = intent.getLongExtra("milliseconds", 0);

        serviceIntent.putExtra("milliseconds", milliseconds);
        serviceIntent.putExtra("rowId", rowId);

        context.startService(serviceIntent);


    }
}
