package dmitrykuznetsov.rememberbirthday.common.alarm;

import io.reactivex.Completable;

/**
 * Created by dmitry on 1/12/18.
 */

public interface AlarmRepo {
    Completable addAlarm(int personId, long millis);


    Completable setAlarmTimeOnToday(int hour, int minute);
    Completable setAlarmTimeOnTomorrow(int hour, int minute);
}
