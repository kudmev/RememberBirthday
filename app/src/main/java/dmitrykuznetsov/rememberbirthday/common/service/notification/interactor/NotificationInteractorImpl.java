package dmitrykuznetsov.rememberbirthday.common.service.notification.interactor;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import io.reactivex.Observable;

/**
 * Created by dmitry on 1/14/18.
 */

public class NotificationInteractorImpl implements NotificationInteractor {

    private PersonRepo personRepo;

    public NotificationInteractorImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Observable<List<PersonData>> getPersonsWaitNotification(long alarmMillis) {
        return personRepo.getPersons("")
                .flatMap((persons) -> filterPersons(persons, alarmMillis));
    }

    private Observable<List<PersonData>> filterPersons(List<PersonData> persons, long alarmMillis) {
        DateTime alarmTime = new DateTime(alarmMillis);
        int alarmTimeDayOfYear = alarmTime.getDayOfYear();
        int nowDayOfYear = DateTime.now().getDayOfYear();

        List<PersonData> birthdaysTodayOrEarlier = new ArrayList<>();
        for (PersonData personData : persons) {
            int personDayOfYear = new DateTime(personData.getDateInMillis()).getDayOfYear();
            if (alarmTimeDayOfYear <= personDayOfYear && personDayOfYear <= nowDayOfYear) {
                birthdaysTodayOrEarlier.add(personData);
            }
        }
        return Observable.just(birthdaysTodayOrEarlier);
    }
}
