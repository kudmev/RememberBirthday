package dmitrykuznetsov.rememberbirthday.common.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

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

    public static final String CLASS_NAME = AlarmRepoImpl.class.getSimpleName();

    private Context context;
    private AlarmManager alarmManager;
    private Config config;

    public AlarmRepoImpl(Context context, AlarmManager alarmManager, Config config) {
        this.context = context;
        this.alarmManager = alarmManager;
        this.config = config;
    }

    @Override
    public Completable setAlarmTime(long millis) {
        DateTime dateTime = new DateTime(millis);
        Log.d(CLASS_NAME, "Alarm set: year: "
                + dateTime.getYear()
                + " month: " + dateTime.getMonthOfYear()
                + " day: " + dateTime.getDayOfMonth()
                + " hour: " + dateTime.getHourOfDay()
                + " minute: " + dateTime.getMinuteOfHour());
        config.set(Constants.ALARM_TIME, millis);

        Intent intentToFire = new Intent(AlarmReceiver.ACTION_REFRESH_DATA);
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
