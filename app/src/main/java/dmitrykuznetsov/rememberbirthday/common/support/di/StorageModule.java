package dmitrykuznetsov.rememberbirthday.common.support.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.support.AppSharedPreferences;
import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.common.support.Convert;
import dmitrykuznetsov.rememberbirthday.common.support.Utils;

/**
 * Created by vernau on 5/26/17.
 */

@Module
public class StorageModule {

    @Provides
    @Singleton
    Config provideConfig(AppSharedPreferences saver, Gson gson, Convert convert, Utils utils) {
        return new Config(saver, gson, convert, utils);
    }

    @Provides
    @Singleton
    AppSharedPreferences provideAppSharedPreferences(SharedPreferences sharedPreferences, Utils utils) {
        return new AppSharedPreferences(sharedPreferences, utils);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        final String config = "config";
        return context.getSharedPreferences(config, Context.MODE_PRIVATE);
    }
}
