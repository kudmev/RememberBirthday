package dmitrykuznetsov.rememberbirthday;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vernau on 5/23/17.
 */

@Module
public class ContextModule {

    @Provides
    Context provideContext(Application application) {
        return application;
    }
}
