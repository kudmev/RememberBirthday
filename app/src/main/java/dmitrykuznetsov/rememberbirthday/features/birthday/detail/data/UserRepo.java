package dmitrykuznetsov.rememberbirthday.features.birthday.detail.data;

import android.content.Context;

import dmitrykuznetsov.rememberbirthday.common.data.PersonData;
import dmitrykuznetsov.rememberbirthday.common.interfaces.ServiceCallback;
import dmitrykuznetsov.rememberbirthday.common.model.Person;

/**
 * Created by dmitry on 11.03.17.
 */

public interface UserRepo {
    void getUser(Context context, int userId, ServiceCallback<PersonData> callBack);
    void deleteUser(Context context, int userId, ServiceCallback<Integer> callBack);
}
