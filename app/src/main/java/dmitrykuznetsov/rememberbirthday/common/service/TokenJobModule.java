package dmitrykuznetsov.rememberbirthday.common.service;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vernau on 6/1/17.
 */

@Module
public class TokenJobModule {

    @Provides
    @Singleton
    TokenJob providesTokenJob(Context context) {
        return new TokenJobImpl(context);
    }
}
