package dmitrykuznetsov.rememberbirthday.features.birthday.add.di;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetriever;
import dmitrykuznetsov.rememberbirthday.common.permissions.PermissionsStorage;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractorImpl;

@Module
public class AddPersonModule {

    @Provides
    AddPersonInteractor provideAddPersonInteractor(PersonRepo personRepo, PhoneRetriever phoneRetriever) {
        return new AddPersonInteractorImpl(personRepo, phoneRetriever);
    }

    @Provides
    AddPersonActivityVM provideAddPersonActivityVm(AddPersonActivity addPersonActivity, AddPersonInteractor addPersonInteractor, PermissionsStorage permissionsStorage) {
        PersonData personData = new PersonData();
        return new AddPersonActivityVM(addPersonActivity, addPersonInteractor, permissionsStorage, personData);
    }

}
