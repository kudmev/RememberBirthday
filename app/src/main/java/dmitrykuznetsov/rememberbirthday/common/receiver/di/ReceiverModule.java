package dmitrykuznetsov.rememberbirthday.common.receiver.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.alarm.AlarmRepo;
import dmitrykuznetsov.rememberbirthday.common.receiver.interactor.AlarmInteractor;
import dmitrykuznetsov.rememberbirthday.common.receiver.interactor.AlarmInteractorImpl;
import dmitrykuznetsov.rememberbirthday.common.support.Config;

/**
 * Created by dmitry on 1/12/18.
 */

@Module
public class ReceiverModule {

    @Provides
    AlarmInteractor provideAlarmInteractor(AlarmRepo alarmRepo, Config config, Context context) {
        return new AlarmInteractorImpl(alarmRepo, config, context);
    }
}
