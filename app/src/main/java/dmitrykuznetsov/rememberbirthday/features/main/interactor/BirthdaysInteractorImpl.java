package dmitrykuznetsov.rememberbirthday.features.main.interactor;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.features.main.model.PersonItemView;
import io.reactivex.Observable;

public class BirthdaysInteractorImpl implements BirthdaysInteractor {

    private PersonRepo personRepo;

    public BirthdaysInteractorImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Observable<List<PersonItemView>> getPersons() {
        return searchPersons(null);
    }

    @Override
    public Observable<List<PersonItemView>> getPersonsByName(String searchName) {
        return searchPersons(searchName);
    }

    private Observable<List<PersonItemView>> searchPersons(String searchText) {
        return personRepo.getPersons(searchText)
                .map(this::sortListByDayOfYear)
                .map(this::moveBirthdaysBeforeNowDay);
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
