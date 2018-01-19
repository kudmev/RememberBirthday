package dmitrykuznetsov.rememberbirthday.common.alarm;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by dmitry on 1/12/18.
 */

public interface AlarmRepo {
    Completable setAlarmTime(long millis);
}
