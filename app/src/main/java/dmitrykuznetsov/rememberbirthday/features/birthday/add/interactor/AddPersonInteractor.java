package dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor;

import android.content.Intent;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonObservable;

/**
 * Created by dmitry on 25.05.17.
 */

public interface AddPersonInteractor {
    void addPersonData(PersonData personData);
    String getPhone(Intent data);
    String getPathImage(Intent data);
}
