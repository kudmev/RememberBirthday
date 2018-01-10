package dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor;

import java.util.List;


import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.model.PersonItemView;
import io.reactivex.Observable;

public interface BirthdaysInteractor {
    Observable<List<PersonItemView>> getPersons();
}
