package dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;

public interface BirthdaysInteractor {
    List<PersonData> getPersonDataList();
}
