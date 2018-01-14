package dmitrykuznetsov.rememberbirthday.features.main.interactor;

import java.util.List;


import dmitrykuznetsov.rememberbirthday.features.main.model.PersonItemView;
import io.reactivex.Observable;

public interface BirthdaysInteractor {
    Observable<List<PersonItemView>> getPersons();
    Observable<List<PersonItemView>> getPersonsByName(String searchText);
}
