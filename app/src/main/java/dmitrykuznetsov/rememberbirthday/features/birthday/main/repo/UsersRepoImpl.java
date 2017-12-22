package dmitrykuznetsov.rememberbirthday.features.birthday.main.repo;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.data.PersonData;
import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by dmitry on 14.05.17.
 */

public class UsersRepoImpl implements UsersRepo {
    @Override
    public List<PersonData> getUsers() {
        Realm realm = Realm.getDefaultInstance();

        List<PersonData> users = realm.where(PersonData.class).findAll().sort(PersonData.DATE, Sort.ASCENDING);

//        List<Person> users = new ArrayList<>();
//        for (PersonData userData: userDataList) {
//            Person person = new Person(userData.getId(), userData.getName(), userData.getNote(), userData.getBindPhone(),
//                    userData.getPathImage(), userData.getDateInMillis());
//        }
        return users;
    }
}
