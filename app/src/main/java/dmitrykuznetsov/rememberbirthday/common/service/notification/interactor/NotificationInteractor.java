package dmitrykuznetsov.rememberbirthday.common.service.notification.interactor;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import io.reactivex.Observable;

/**
 * Created by dmitry on 1/14/18.
 */

public interface NotificationInteractor {
    Observable<List<PersonData>> getPersonsWaitNotification(long alarmMillis);
}
