package dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.data.PersonData;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.repo.UsersRepo;

/**
 * Created by Alena on 26.12.2017.
 */

public class BirthdaysInteractorImpl implements BirthdaysInteractor {

    private UsersRepo usersRepo;

    public BirthdaysInteractorImpl(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public List<PersonData> getPersonDataList() {
        return usersRepo.getUsers();
    }
}
