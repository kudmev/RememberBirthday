package dmitrykuznetsov.rememberbirthday.features.birthday.add.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractorImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.AddPersonRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.AddPersonRepoImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.LastPersonIdRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.LastPersonIdRepoImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.PhoneRetriever;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.PhoneRetrieverImpl;

/**
 * Created by Alena on 26.12.2017.
 */
@Module
public class AddPersonModule {

    @Provides
    PhoneRetriever providePhoneRetriever(Context context) {
        return new PhoneRetrieverImpl(context);
    }

    @Provides
    AddPersonRepo provideAddPersonRepo(){
        return new AddPersonRepoImpl();
    }

    @Provides
    LastPersonIdRepo provideLastPersonIdRepo() {
        return new LastPersonIdRepoImpl();
    }

    @Provides
    AddPersonInteractor provideAddPersonInteractor(LastPersonIdRepo lastPersonIdRepo, AddPersonRepo addPersonRepo, PhoneRetriever phoneRetriever) {
        return new AddPersonInteractorImpl(lastPersonIdRepo, addPersonRepo, phoneRetriever);
    }

    @Provides
    AddPersonActivityVM provideAddPersonActivityVm(AddPersonActivity addPersonActivity, AddPersonInteractor addPersonInteractor) {
        return new AddPersonActivityVM(addPersonActivity, addPersonInteractor);
    }

}
