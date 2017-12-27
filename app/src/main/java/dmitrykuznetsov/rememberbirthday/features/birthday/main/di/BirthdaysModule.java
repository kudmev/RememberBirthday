package dmitrykuznetsov.rememberbirthday.features.birthday.main.di;

import android.util.Log;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.BirthdaysActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.BirthdaysActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor.BirthdaysInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor.BirthdaysInteractorImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.repo.UsersRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.repo.UsersRepoImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.scope.Birthdays;

/**
 * Created by Alena on 26.12.2017.
 */

@Module
public class BirthdaysModule {

    @Provides
    @Singleton
    UsersRepo provideUserRepo(){
        return new UsersRepoImpl();
    }

    @Provides
    @Singleton
    BirthdaysInteractor provideBirthdayInteractor(@Birthdays UsersRepo usersRepo) {
        return new BirthdaysInteractorImpl(usersRepo);
    }

    @Provides
    @Birthdays
    BirthdaysActivityVM provideBirthdayActivityVM(BirthdaysActivity activity,
                                                  /*@Named("recycler_linear") */ RecyclerConfiguration configuration,
                                                   BirthdaysInteractor interactor) {
        Log.e("Dagger", "Provide BirthdayActivityVM");
        return new BirthdaysActivityVM(activity, configuration, interactor);
    }
}
