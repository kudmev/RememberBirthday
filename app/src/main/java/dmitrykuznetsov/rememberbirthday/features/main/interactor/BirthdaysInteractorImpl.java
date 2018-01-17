package dmitrykuznetsov.rememberbirthday.features.main.interactor;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.alarm.AlarmRepo;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.features.main.model.PersonItemView;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BirthdaysInteractorImpl implements BirthdaysInteractor {

    private PersonRepo personRepo;
    private AlarmRepo alarmRepo;
    private Config config;

    public BirthdaysInteractorImpl(PersonRepo personRepo, AlarmRepo alarmRepo, Config config) {
        this.personRepo = personRepo;
        this.alarmRepo = alarmRepo;
        this.config = config;
    }

    @Override
    public Observable<List<PersonItemView>> getPersons() {
        return searchPersons(null);
    }

    @Override
    public Observable<List<PersonItemView>> getPersonsByName(String searchName) {
        return searchPersons(searchName);
    }

    @Override
    public Completable setInitialAlarm() {
        long alarmMillis = config.getAsLong(Constants.ALARM_TIME);
        if (alarmMillis == 0) {
            return alarmRepo.setAlarmTime(true, 10, 0)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            return Completable.complete();
        }
    }

    private Observable<List<PersonItemView>> searchPersons(String searchText) {
        return personRepo.getPersons(searchText)
                .map(this::sortListByDayOfYear)
                .map(this::moveBirthdaysBeforeNowDay)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<PersonItemView> sortListByDayOfYear(List<PersonData> persons) {
        List<PersonItemView> personItemViews = new ArrayList<>();
        for (PersonData personData : persons) {
            long dateInMillis = personData.getDateInMillis();
            DateTime dateTimePerson = new DateTime(dateInMillis);
            int dayOfYearPerson = dateTimePerson.getDayOfYear();
            personItemViews.add(new PersonItemView(dayOfYearPerson, personData));
        }
        Collections.sort(personItemViews);
        return personItemViews;
    }

    private List<PersonItemView> moveBirthdaysBeforeNowDay(List<PersonItemView> personItemViews) {
        int nowDay = DateTime.now().getDayOfYear();
        List<PersonItemView> listBeforeDayNow = new ArrayList<>();

        Iterator<PersonItemView> iterator = personItemViews.iterator();
        while (iterator.hasNext()) {
            PersonItemView personItemView = iterator.next();
            int dayPerson = personItemView.getDayOfYear();
            if (dayPerson < nowDay) {
                listBeforeDayNow.add(personItemView);
                iterator.remove();
            }
        }
        personItemViews.addAll(listBeforeDayNow);
        return personItemViews;
    }
}
