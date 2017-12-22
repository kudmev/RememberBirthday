package dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor;

import android.net.Uri;

import dmitrykuznetsov.rememberbirthday.common.data.PersonData;
import dmitrykuznetsov.rememberbirthday.common.model.Person;

/**
 * Created by dmitry on 25.05.17.
 */

public interface AddPersonInteractor {
    void addPersonData(Person personData);

    String getPhone(Uri uri);


}
