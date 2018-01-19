package dmitrykuznetsov.rememberbirthday.features.help.di;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.features.help.HelpActivity;
import dmitrykuznetsov.rememberbirthday.features.help.HelpActivityVM;
import dmitrykuznetsov.rememberbirthday.features.main.scope.ActivityScope;

/**
 * Created by dmitry on 1/19/18.
 */

@Module
public class HelpModule {

    @Provides
    @ActivityScope
    HelpActivityVM provideHelpActivityVM(HelpActivity activity){
        return new HelpActivityVM(activity);
    }
}
