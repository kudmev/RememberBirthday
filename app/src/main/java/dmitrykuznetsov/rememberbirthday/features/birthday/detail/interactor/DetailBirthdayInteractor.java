package dmitrykuznetsov.rememberbirthday.features.birthday.detail.interactor;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import io.reactivex.Observable;

public interface DetailBirthdayInteractor {
    Observable<Boolean> deletePerson(int personId);
    Observable<PersonData> getPerson(int personId);
}
