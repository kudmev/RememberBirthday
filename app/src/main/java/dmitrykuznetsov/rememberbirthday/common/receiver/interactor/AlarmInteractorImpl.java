package dmitrykuznetsov.rememberbirthday.common.receiver.interactor;

import android.content.Context;
import android.content.Intent;

import org.joda.time.DateTime;

import dmitrykuznetsov.rememberbirthday.common.alarm.AlarmRepo;
import dmitrykuznetsov.rememberbirthday.common.receiver.model.AlarmTime;
import dmitrykuznetsov.rememberbirthday.common.service.notification.NotificationService;
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
    private Context context;

    public AlarmInteractorImpl(AlarmRepo alarmRepo, Context context) {
        this.alarmRepo = alarmRepo;
        this.context = context;
    }

    @Override
    public Completable updateAlarm(long alarmMillis) {
        return Completable.fromObservable(getAlarmTime(alarmMillis)
                .map((a) -> alarmRepo.setAlarmTime(a.isNeedPlusDay, a.hour, a.minute)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable runNotificationPersons(long millis) {
        return Completable.fromObservable(getAlarmTime(millis)
                .map((alarmTime -> runService(alarmTime, millis))));
    }

    private Observable<AlarmTime> getAlarmTime(long alarmMillis) {
        long now = DateTime.now().getMillis();

        DateTime alarmDateTime = new DateTime(alarmMillis);
        int hour = alarmDateTime.getHourOfDay();
        int minute = alarmDateTime.getMinuteOfHour();
        boolean isNeedPlusDay = alarmMillis < now;

        AlarmTime alarmTime = new AlarmTime(isNeedPlusDay, hour, minute);
        return Observable.just(alarmTime);
    }

    private Completable runService(AlarmTime alarmTime, long alarmMillis) {
        if (alarmTime.isNeedPlusDay) {
            Intent intent = new Intent(context, NotificationService.class);
            intent.putExtra(Constants.ALARM_TIME, alarmMillis);
            context.startService(intent);
        }
        return Completable.complete();
    }
}
