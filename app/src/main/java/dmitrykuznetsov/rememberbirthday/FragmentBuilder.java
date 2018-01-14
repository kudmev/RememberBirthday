package dmitrykuznetsov.rememberbirthday;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dmitrykuznetsov.rememberbirthday.features.settings.SettingsFragment;
import dmitrykuznetsov.rememberbirthday.features.settings.di.SettingsModule;

/**
 * Created by vernau on 21.08.17.
 */

@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = SettingsModule.class)
    abstract SettingsFragment bindSettingsFragment();
}
