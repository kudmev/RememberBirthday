package dmitrykuznetsov.rememberbirthday.common.data.repo;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;

/**
 * Created by dmitry on 14.05.17.
 */

public interface PersonRepo {
    List<PersonData> getPersons();
    void addPerson(PersonData personData);
    int getPersonNextId();
}
