package dmitrykuznetsov.rememberbirthday.common.receiver.interactor;

import io.reactivex.Completable;

/**
 * Created by dmitry on 1/14/18.
 */

public interface AlarmInteractor {
    Completable updateAlarm();
    Completable runNotificationPersons();
}
