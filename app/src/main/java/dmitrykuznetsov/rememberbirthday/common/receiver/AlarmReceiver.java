package dmitrykuznetsov.rememberbirthday.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dmitrykuznetsov.rememberbirthday.common.receiver.interactor.AlarmInteractor;

/**
 * Created by Dmitry Kuznetsov on 23.07.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final String TAG = AlarmReceiver.class.getSimpleName();
    public static final String ACTION_TIME_SET ="android.intent.action.TIME_SET";
    public static final String ACTION_TIMEZONE_CHANGED ="android.intent.action.TIMEZONE_CHANGED";
    public static final String ACTION_REFRESH_DATA = "dmitrykuznetsov.rememberbirthday.ACTION_REFRESH_DATA";
    public static final String ACTION_PACKAGE_REPLACED = "android.intent.action.MY_PACKAGE_REPLACED";
//    public static final String ACTION_INSTALL = "com.android.vending.INSTALL_REFERRER";

    @Inject
    AlarmInteractor alarmInteractor;

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);
        Log.d(TAG, "onReceive");
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case ACTION_REFRESH_DATA:
                case ACTION_PACKAGE_REPLACED:
                case ACTION_TIME_SET:
                case ACTION_TIMEZONE_CHANGED:
                    Log.d(TAG, "ACTION_");
                    alarmInteractor.runNotificationPersons()
                            .subscribe();
                    alarmInteractor.updateAlarm()
                            .subscribe();
                    break;
            }
        }
    }

}
