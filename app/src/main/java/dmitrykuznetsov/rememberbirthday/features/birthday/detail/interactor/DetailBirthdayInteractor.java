package dmitrykuznetsov.rememberbirthday.features.birthday.detail.interactor;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DetailBirthdayInteractor {
    Completable deletePerson(int personId);
    Observable<PersonData> getPerson(int personId);
}
