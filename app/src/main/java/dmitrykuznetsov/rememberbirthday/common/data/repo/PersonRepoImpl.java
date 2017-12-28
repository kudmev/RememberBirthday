package dmitrykuznetsov.rememberbirthday.common.data.repo;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by dmitry on 14.05.17.
 */

public class PersonRepoImpl implements PersonRepo {
    @Override
    public List<PersonData> getPersons() {
        Realm realm = Realm.getDefaultInstance();

        List<PersonData> users = realm.where(PersonData.class).findAll().sort(PersonData.DATE, Sort.ASCENDING);

//        List<Person> users = new ArrayList<>();
//        for (PersonData userData: userDataList) {
//            Person person = new Person(userData.getId(), userData.getName(), userData.getNote(), userData.getBindPhone(),
//                    userData.getPathImage(), userData.getDateInMillis());
//        }
        return users;
    }

    @Override
    public void addPerson(PersonData personData) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        PersonData newPersonData = realm.copyToRealm(personData);
        realm.commitTransaction();
    }

    @Override
    public int getPersonNextId() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<PersonData> results = realm.where(PersonData.class).findAll();

        int lastId = 0;
        if (results != null && results.size() != 0) {
            PersonData personData = results.sort(PersonData.ID, Sort.ASCENDING).last();
            lastId = personData.getId();
        }
        lastId++;
        return lastId;
    }
}
