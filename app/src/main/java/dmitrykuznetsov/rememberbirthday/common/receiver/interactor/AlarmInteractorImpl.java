package dmitrykuznetsov.rememberbirthday.common.receiver.interactor;

import android.content.Context;
import android.content.Intent;

import org.joda.time.DateTime;

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

    private AlarmRepo alarmRepo;
    private Config config;
    private Context context;

    public AlarmInteractorImpl(AlarmRepo alarmRepo, Config config, Context context) {
        this.alarmRepo = alarmRepo;
        this.config = config;
        this.context = context;
    }

    @Override
    public Completable updateAlarm() {
        return Completable.fromObservable(getAlarmTime()
                .map((a) -> alarmRepo.setAlarmTime(a.isNeedPlusDay, a.hour, a.minute)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable runNotificationPersons() {
        return Completable.fromObservable(getAlarmTime()
                .map((this::runService)));
    }

    private Observable<AlarmTime> getAlarmTime() {
        long alarmMillis = config.getAsLong(Constants.ALARM_TIME);
        AlarmTime alarmTime;
        if (alarmMillis == 0) {
            alarmMillis = new DateTime()
                    .plusDays(1)
                    .withHourOfDay(10)
                    .withMinuteOfHour(0)
                    .withSecondOfMinute(0)
                    .withMillisOfSecond(0)
                    .getMillis();
            alarmTime = new AlarmTime(alarmMillis, false, 10, 0);
        } else {
            long now = DateTime.now().getMillis();
            DateTime alarmDateTime = new DateTime(alarmMillis);
            int hour = alarmDateTime.getHourOfDay();
            int minute = alarmDateTime.getMinuteOfHour();
            boolean isNeedPlusDay = alarmMillis < now;
            alarmTime = new AlarmTime(alarmMillis, isNeedPlusDay, hour, minute);
        }

        return Observable.just(alarmTime);
    }

    private Completable runService(AlarmTime alarmTime) {
        if (alarmTime.isNeedPlusDay) {
            Intent intent = new Intent(context, NotificationService.class);
            intent.putExtra(Constants.ALARM_TIME, alarmTime.alarmMillis);
            context.startService(intent);
        }
        return Completable.complete();
    }
}
