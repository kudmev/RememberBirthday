package dmitrykuznetsov.rememberbirthday;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Dmitry Kuznetsov on 23.07.2015.
 */
public class AlarmNotificationReceiver extends BroadcastReceiver {
    public static final String ACTION_SEND_NOTIFICATION = "dmitrykuznetsov.rememberbirthday.ACTION_SEND_NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("start", "AlarmNotificationReceiver");
        Intent servIntent = new Intent(context, ServiceSendNotification.class);
        int rowId = intent.getIntExtra("rowId", 0);
        long milliseconds = intent.getLongExtra("milliseconds", 0);

        servIntent.putExtra("milliseconds", milliseconds);
        servIntent.putExtra("rowId", rowId);

        context.startService(servIntent);


    }
}
