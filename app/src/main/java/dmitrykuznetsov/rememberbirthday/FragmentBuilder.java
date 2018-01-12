package dmitrykuznetsov.rememberbirthday;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dmitrykuznetsov.rememberbirthday.common.service.notification.di.ServiceSendModule;
import dmitrykuznetsov.rememberbirthday.features.birthday.settings.SettingsFragment;
import dmitrykuznetsov.rememberbirthday.features.birthday.settings.di.SettingsModule;

/**
 * Created by vernau on 21.08.17.
 */

@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = SettingsModule.class)
    abstract SettingsFragment bindSettingsFragment();
}
