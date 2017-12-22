package dmitrykuznetsov.rememberbirthday.features.birthday.add.repo;

import dmitrykuznetsov.rememberbirthday.common.data.PersonData;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by dmitry on 14.05.17.
 */

public class LastPersonRepoImpl implements LastPersonRepo {
    @Override
    public int getNextId() {
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
