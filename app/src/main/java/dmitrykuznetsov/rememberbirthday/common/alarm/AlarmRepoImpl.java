package dmitrykuznetsov.rememberbirthday.common.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import org.joda.time.DateTime;

import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.common.receiver.AlarmReceiver;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitry on 1/12/18.
 */

public class AlarmRepoImpl implements AlarmRepo {

    private Context context;
    private AlarmManager alarmManager;
    private Config config;

    public AlarmRepoImpl(Context context, AlarmManager alarmManager, Config config) {
        this.context = context;
        this.alarmManager = alarmManager;
        this.config = config;
    }

//    @Override
//    public Completable setAlarmTimeOnToday(int hour, int minute) {
//        return setAlarmTime(hour, minute, false)
//                .map(this::saveTime);
//    }
//
//    @Override
//    public Completable setAlarmTimeOnTomorrow(int hour, int minute) {
//        return setAlarmTime(hour, minute, true)
//                .flatMap(this::saveTime);
//    }

    @Override
    public Completable setAlarmTime(boolean isNeedPlusDay, int hour, int minute) {
        Intent intentToFire = new Intent(AlarmReceiver.ACTION_REFRESH_DATA);

        int day = isNeedPlusDay ? 1 : 0;
        DateTime dateTime = new DateTime()
                .plusDays(day)
                .withHourOfDay(hour)
                .withMinuteOfHour(minute)
                .withSecondOfMinute(0)
                .withMillisOfSecond(0);

        long millis = dateTime.getMillis();
        config.set(Constants.ALARM_TIME, millis);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentToFire, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT < 23) {
            if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        }
        return Completable.complete();
    }

}
