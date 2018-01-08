package dmitrykuznetsov.rememberbirthday.features.birthday.detail.interactor;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import io.reactivex.Observable;

public class DetailBirthdayInteractorImpl implements DetailBirthdayInteractor {

    private PersonRepo personRepo;

    public DetailBirthdayInteractorImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Observable<Boolean> deletePerson(int personId) {
        return personRepo.deletePerson(personId);
    }


}
