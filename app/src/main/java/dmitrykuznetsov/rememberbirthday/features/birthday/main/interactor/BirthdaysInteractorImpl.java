package dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import io.reactivex.Observable;

public class BirthdaysInteractorImpl implements BirthdaysInteractor {

    private PersonRepo personRepo;

    public BirthdaysInteractorImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Observable<List<PersonData>> getPersons() {
        return personRepo.getPersons();
    }
}
