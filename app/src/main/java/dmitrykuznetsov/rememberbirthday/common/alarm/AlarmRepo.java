package dmitrykuznetsov.rememberbirthday.common.alarm;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by dmitry on 1/12/18.
 */

public interface AlarmRepo {
    Completable setAlarmTime(boolean isNeedPlusDay, int hour, int minute);

//    Completable setAlarmTimeOnToday(int hour, int minute);
//    Completable setAlarmTimeOnTomorrow(int hour, int minute);
}
