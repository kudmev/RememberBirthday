package dmitrykuznetsov.rememberbirthday.common.receiver.interactor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.joda.time.DateTime;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.alarm.AlarmRepo;
import dmitrykuznetsov.rememberbirthday.common.receiver.model.AlarmTime;
import dmitrykuznetsov.rememberbirthday.common.service.notification.NotificationService;
import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitry on 1/14/18.
 */

public class AlarmInteractorImpl implements AlarmInteractor {

    private static final String TAG = AlarmInteractorImpl.class.getSimpleName();

    private AlarmRepo alarmRepo;
    private Config config;
    private SharedPreferences sharedPreferences;
    private Context context;

    public AlarmInteractorImpl(AlarmRepo alarmRepo, Config config, SharedPreferences sharedPreferences, Context context) {
        this.alarmRepo = alarmRepo;
        this.config = config;
        this.sharedPreferences = sharedPreferences;
        this.context = context;
    }

    @Override
    public Completable runNotificationPersons() {
        return Completable.fromObservable(getAlarmTimeFromStorage()
                .map((this::runService)));
    }

    @Override
    public Completable updateAlarm() {
        return Completable.fromObservable(
//                getAlarmTimeFromStorage()
                createAlarmTimeOnTomorrow()
                .map((millis) -> alarmRepo.setAlarmTime(millis)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Long> getAlarmTimeFromStorage() {
        long alarmMillis = config.getAsLong(Constants.ALARM_TIME);
        return Observable.just(alarmMillis);
    }

    private Observable<Long> createAlarmTimeOnTomorrow() {
        String time = sharedPreferences.getString(Constants.timePrefA_Key, context.getString(R.string.default_alarm_time));
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));

        long alarmMillisTomorrow = new DateTime()
                .plusDays(1)
                .withHourOfDay(hour)
                .withMinuteOfHour(minute)
                .withSecondOfMinute(0)
                .withMillisOfSecond(0)
                .getMillis();

        return Observable.just(alarmMillisTomorrow);
    }

    private Completable runService(long millis) {
        if (millis != 0) {
            Intent intent = new Intent(context, NotificationService.class);
            intent.putExtra(Constants.ALARM_TIME, millis);
            context.startService(intent);
        }
            return Completable.complete();
        }
    }
