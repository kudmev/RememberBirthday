package dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor;

import java.util.List;


import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import io.reactivex.Observable;

public interface BirthdaysInteractor {
    Observable<List<PersonData>> getPersons();
}
