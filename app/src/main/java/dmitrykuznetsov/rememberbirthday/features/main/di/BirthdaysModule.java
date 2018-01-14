package dmitrykuznetsov.rememberbirthday.features.main.di;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.features.main.BirthdaysActivity;
import dmitrykuznetsov.rememberbirthday.features.main.BirthdaysActivityVM;
import dmitrykuznetsov.rememberbirthday.features.main.interactor.BirthdaysInteractor;
import dmitrykuznetsov.rememberbirthday.features.main.interactor.BirthdaysInteractorImpl;
import dmitrykuznetsov.rememberbirthday.features.main.model.PersonItemView;
import dmitrykuznetsov.rememberbirthday.features.main.scope.ActivityScope;

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
        List<PersonItemView> persons = new ArrayList<>();
        return new BirthdaysActivityVM(activity, persons, configuration, interactor);
    }
}
