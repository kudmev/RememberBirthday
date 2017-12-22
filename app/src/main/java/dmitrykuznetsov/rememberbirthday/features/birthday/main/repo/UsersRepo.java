package dmitrykuznetsov.rememberbirthday.features.birthday.main.repo;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.data.PersonData;

/**
 * Created by dmitry on 14.05.17.
 */

public interface UsersRepo {
    List<PersonData> getUsers();
}
