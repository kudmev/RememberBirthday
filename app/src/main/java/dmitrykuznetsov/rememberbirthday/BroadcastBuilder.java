package dmitrykuznetsov.rememberbirthday;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dmitrykuznetsov.rememberbirthday.common.receiver.AlarmReceiver;
import dmitrykuznetsov.rememberbirthday.common.receiver.di.AlarmReceiverModule;

/**
 * Created by dmitry on 1/12/18.
 */

@Module
public abstract class BroadcastBuilder {

    @ContributesAndroidInjector(modules = AlarmReceiverModule.class)
    abstract AlarmReceiver bindAlarmReceiver();
}
