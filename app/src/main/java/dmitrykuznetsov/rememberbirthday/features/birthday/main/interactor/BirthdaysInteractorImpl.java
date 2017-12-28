package dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;

/**
 * Created by Alena on 26.12.2017.
 */

public class BirthdaysInteractorImpl implements BirthdaysInteractor {

    private PersonRepo personRepo;

    public BirthdaysInteractorImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public List<PersonData> getPersonDataList() {
        return personRepo.getPersons();
    }
}