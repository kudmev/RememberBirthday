package dmitrykuznetsov.rememberbirthday.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.joda.time.DateTime;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DaggerBroadcastReceiver;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dmitrykuznetsov.rememberbirthday.common.alarm.AlarmRepo;
import dmitrykuznetsov.rememberbirthday.common.service.notification.ServiceSendNotification;
import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;

/**
 * Created by Dmitry Kuznetsov on 23.07.2015.
 */
public class AlarmReceiver extends BroadcastReceiver  {

    public static final String ACTION_REFRESH_DATA = "dmitrykuznetsov.rememberbirthday.ACTION_REFRESH_DATA";
//
//    @Inject
//    DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector;

    @Inject
    AlarmRepo alarmRepo;

    @Inject
    Config config;

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);
//        super.onReceive(context, intent);

//        super.onReceive(context, intent);
        Log.d("AlarmReceiver", "AlarmReceiver");
//        int hour = intent.getIntExtra(Constants.ALARM_HOUR, 0);
//        int minute = intent.getIntExtra(Constants.ALARM_MINUTE, 0);

        long alarmMillis = config.getAsLong(Constants.ALARM_TIME);
        DateTime alarmTime = new DateTime(alarmMillis);
        int hour = alarmTime.getHourOfDay();
        int minute = alarmTime.getMinuteOfHour();

        long now = DateTime.now().getMillis();
        if (now < alarmMillis) {
            alarmRepo.setAlarmTimeOnToday(hour, minute)
                    .subscribe(this::onSuccess, this::onError);
        } else {
            alarmRepo.setAlarmTimeOnTomorrow(hour, minute)
                    .subscribe(this::onSuccess, this::onError);
            Intent serviceIntent = new Intent(context, ServiceSendNotification.class);
            serviceIntent.putExtra(Constants.ALARM_TIME, alarmMillis);
            context.startService(serviceIntent);
        }


    }

    private void onSuccess() {
        Log.d("Alarm", "success");
    }

    private void onError(Throwable throwable) {
        Log.d("Alarm", "error");
    }

//
//    @Override
//    public AndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
//        return broadcastReceiverInjector;
//    }
}
