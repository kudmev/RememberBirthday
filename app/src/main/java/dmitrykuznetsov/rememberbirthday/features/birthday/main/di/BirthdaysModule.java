package dmitrykuznetsov.rememberbirthday.features.birthday.main.di;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.BirthdaysActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.BirthdaysActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor.BirthdaysInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor.BirthdaysInteractorImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.scope.ActivityScope;

/**
 * Created by Alena on 26.12.2017.
 */

@Module
public class BirthdaysModule {

    @Provides
    @ActivityScope
    BirthdaysInteractor provideBirthdayInteractor(PersonRepo personRepo) {
        return new BirthdaysInteractorImpl(personRepo);
    }

    @Provides
    @ActivityScope
    BirthdaysActivityVM provideBirthdayActivityVM(BirthdaysActivity activity,
                                                    RecyclerConfiguration configuration,
                                                    BirthdaysInteractor interactor) {
        return new BirthdaysActivityVM(activity, configuration, interactor);
    }
}
