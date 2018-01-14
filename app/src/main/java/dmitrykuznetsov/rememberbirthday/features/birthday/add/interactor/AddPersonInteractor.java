package dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor;

import android.content.Intent;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import io.reactivex.Completable;

/**
 * Created by dmitry on 25.05.17.
 */

public interface AddPersonInteractor {
    Completable addPersonData(PersonData personData);

    Completable updatePersonData(PersonData personData);

    String getPhone(Intent data);

    String getPathImage(Intent data);
}
