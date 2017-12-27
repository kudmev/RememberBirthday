package dmitrykuznetsov.rememberbirthday;

import javax.inject.Singleton;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dmitrykuznetsov.rememberbirthday.common.adapter.di.RecyclerModule;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.di.AddPersonModule;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.BirthdaysActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.di.BirthdaysModule;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.scope.Birthdays;

/**
 * Created by vernau on 21.08.17.
 */

@Module
public abstract class ActivityBuilder {

//    @ContributesAndroidInjector(modules = AddPersonModule.class)
//    abstract AddPersonActivity bindAddPersonActivity();

    @Birthdays
    @ContributesAndroidInjector(modules = BirthdaysModule.class)
    abstract BirthdaysActivity bindBirthdaysActivity();

}
