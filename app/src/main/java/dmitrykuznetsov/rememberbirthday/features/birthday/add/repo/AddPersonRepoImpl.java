package dmitrykuznetsov.rememberbirthday.features.birthday.add.repo;

import dmitrykuznetsov.rememberbirthday.data.PersonData;
import io.realm.Realm;

/**
 * Created by dmitry on 14.05.17.
 */

public class AddPersonRepoImpl implements AddPersonRepo {
    @Override
    public void addPerson(PersonData personData) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        PersonData newPersonData = realm.copyToRealm(personData);
        realm.commitTransaction();
    }
}
