package dmitrykuznetsov.rememberbirthday.features.birthday.edit.di;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetriever;
import dmitrykuznetsov.rememberbirthday.common.permissions.PermissionsStorage;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractorImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.edit.EditPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.edit.EditPersonActivityVM;

@Module
public class EditPersonModule {

    @Provides
    AddPersonInteractor provideAddPersonInteractor(PersonRepo personRepo, PhoneRetriever phoneRetriever) {
        return new AddPersonInteractorImpl(personRepo, phoneRetriever);
    }

    @Provides
    AddPersonActivityVM provideAddPersonActivityVm(EditPersonActivity editPersonActivity, AddPersonInteractor addPersonInteractor, PermissionsStorage permissionsStorage) {
        PersonData personData = new PersonData();
        return new AddPersonActivityVM(editPersonActivity, addPersonInteractor, permissionsStorage, personData);
    }


    @Provides
    EditPersonActivityVM provideEditPersonActivityVm(EditPersonActivity editPersonActivity, AddPersonInteractor addPersonInteractor, PermissionsStorage permissionsStorage) {
        PersonData personData = editPersonActivity.getIntent().getParcelableExtra(Constants.PERSON_DATA);
        return new EditPersonActivityVM(editPersonActivity, addPersonInteractor, permissionsStorage, personData);
    }

}
