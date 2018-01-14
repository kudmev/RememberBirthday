package dmitrykuznetsov.rememberbirthday.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dmitrykuznetsov.rememberbirthday.common.receiver.interactor.AlarmInteractor;
import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;

/**
 * Created by Dmitry Kuznetsov on 23.07.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final String ACTION_REFRESH_DATA = "dmitrykuznetsov.rememberbirthday.ACTION_REFRESH_DATA";

    @Inject
    AlarmInteractor alarmInteractor;

    @Inject
    Config config;

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);

        if (ACTION_REFRESH_DATA.equals(intent.getAction())) {
            long alarmMillis = config.getAsLong(Constants.ALARM_TIME);
            alarmInteractor.updateAlarm(alarmMillis)
                    .subscribe();
            alarmInteractor.runNotificationPersons(alarmMillis)
                    .subscribe();
        }
    }

}
