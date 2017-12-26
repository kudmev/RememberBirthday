package dmitrykuznetsov.rememberbirthday.old;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Dmitry Kuznetsov on 23.07.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static final String ACTION_REFRESH_DATA = "dmitrykuznetsov.rememberbirthday.ACTION_REFRESH_DATA";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.d("AlarmReceiver", "AlarmReceiver");
//        Toast.makeText(context, "Запустилась сигнализация обновления",
//                Toast.LENGTH_LONG).show();
        Intent servIntent = new Intent(context, ServiceDateUpdater.class);
        context.startService(servIntent);

    }


}
