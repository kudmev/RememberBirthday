package dmitrykuznetsov.rememberbirthday;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dmitrykuznetsov.rememberbirthday.common.receiver.di.ReceiverModule;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.di.AddPersonModule;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.DetailBirthdayActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.di.DetailBirthdayModule;
import dmitrykuznetsov.rememberbirthday.features.birthday.edit.EditPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.edit.di.EditPersonModule;
import dmitrykuznetsov.rememberbirthday.features.help.HelpActivity;
import dmitrykuznetsov.rememberbirthday.features.help.di.HelpModule;
import dmitrykuznetsov.rememberbirthday.features.main.BirthdaysActivity;
import dmitrykuznetsov.rememberbirthday.features.main.di.BirthdaysModule;
import dmitrykuznetsov.rememberbirthday.features.main.scope.ActivityScope;

/**
 * Created by vernau on 21.08.17.
 */

@Module
public abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = {BirthdaysModule.class, ReceiverModule.class})
    abstract BirthdaysActivity bindBirthdaysActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = AddPersonModule.class)
    abstract AddPersonActivity bindAddPersonActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {EditPersonModule.class})
    abstract EditPersonActivity bindEditPersonActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = DetailBirthdayModule.class)
    abstract DetailBirthdayActivity bindDetailsPersonActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = HelpModule.class)
    abstract HelpActivity bindHelpActivity();


}
