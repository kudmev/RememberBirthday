package dmitrykuznetsov.rememberbirthday.common.data.repo;

import java.util.List;


import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import io.reactivex.Observable;

/**
 * Created by dmitry on 14.05.17.
 */

public interface PersonRepo {
    Observable<List<PersonData>> getPersons();

    void addPerson(PersonData personData);

    int getLastPersonId();

    Observable<Boolean> deletePerson(int personId);

    Observable<PersonData> getPerson(int personId);
}