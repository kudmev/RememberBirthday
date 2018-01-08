package dmitrykuznetsov.rememberbirthday.features.birthday.detail.interactor;

import io.reactivex.Observable;

public interface DetailBirthdayInteractor {
    Observable<Boolean> deletePerson(int personId);
}
