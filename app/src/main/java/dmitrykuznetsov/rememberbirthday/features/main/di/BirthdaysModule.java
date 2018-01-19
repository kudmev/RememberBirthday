package dmitrykuznetsov.rememberbirthday.features.main.di;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;
import dmitrykuznetsov.rememberbirthday.common.alarm.AlarmRepo;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.receiver.interactor.AlarmInteractor;
import dmitrykuznetsov.rememberbirthday.common.support.Config;
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
    BirthdaysInteractor provideBirthdayInteractor(PersonRepo personRepo, AlarmRepo alarmRepo, Config config) {
        return new BirthdaysInteractorImpl(personRepo, alarmRepo, config);
    }

    @Provides
    @ActivityScope
    BirthdaysActivityVM provideBirthdayActivityVM(BirthdaysActivity activity,
                                                  RecyclerConfiguration configuration,
                                                  BirthdaysInteractor bInteractor, AlarmInteractor aInteractor) {
        List<PersonItemView> persons = new ArrayList<>();
        return new BirthdaysActivityVM(activity, persons, configuration, bInteractor, aInteractor);
    }
}
