package dmitrykuznetsov.rememberbirthday;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dmitrykuznetsov.rememberbirthday.common.receiver.AlarmReceiver;
import dmitrykuznetsov.rememberbirthday.common.receiver.UpgradeVersionReceiver;
import dmitrykuznetsov.rememberbirthday.common.receiver.di.ReceiverModule;

/**
 * Created by dmitry on 1/12/18.
 */

@Module
public abstract class BroadcastBuilder {

    @ContributesAndroidInjector(modules = ReceiverModule.class)
    abstract AlarmReceiver bindAlarmReceiver();

    @ContributesAndroidInjector(modules = ReceiverModule.class)
    abstract UpgradeVersionReceiver bindUpgradeVersionReceiver();
}
