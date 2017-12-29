package dmitrykuznetsov.rememberbirthday.features.birthday.add.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractorImpl;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetriever;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetrieverImpl;

@Module
public class AddPersonModule {

    @Provides
    AddPersonInteractor provideAddPersonInteractor(PersonRepo personRepo, PhoneRetriever phoneRetriever) {
        return new AddPersonInteractorImpl(personRepo, phoneRetriever);
    }

    @Provides
    AddPersonActivityVM provideAddPersonActivityVm(AddPersonActivity addPersonActivity, AddPersonInteractor addPersonInteractor) {
        return new AddPersonActivityVM(addPersonActivity, addPersonInteractor);
    }

}
