package dmitrykuznetsov.rememberbirthday.features.birthday.main.di;

import android.util.Log;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.BirthdaysActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.BirthdaysActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor.BirthdaysInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor.BirthdaysInteractorImpl;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepoImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.scope.Birthdays;

/**
 * Created by Alena on 26.12.2017.
 */

@Module
public class BirthdaysModule {

    @Provides
    @Birthdays
    BirthdaysInteractor provideBirthdayInteractor(@Birthdays PersonRepo personRepo) {
        return new BirthdaysInteractorImpl(personRepo);
    }

    @Provides
    @Birthdays
    BirthdaysActivityVM provideBirthdayActivityVM(BirthdaysActivity activity,
                                                    RecyclerConfiguration configuration,
                                                    BirthdaysInteractor interactor) {
        return new BirthdaysActivityVM(activity, configuration, interactor);
    }
}
