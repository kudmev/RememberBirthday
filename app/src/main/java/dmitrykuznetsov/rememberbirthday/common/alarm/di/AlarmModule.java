package dmitrykuznetsov.rememberbirthday.common.alarm.di;

import android.app.AlarmManager;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.alarm.AlarmRepo;
import dmitrykuznetsov.rememberbirthday.common.alarm.AlarmRepoImpl;
import dmitrykuznetsov.rememberbirthday.common.support.Config;

/**
 * Created by dmitry on 1/12/18.
 */

@Module
public class AlarmModule {

    @Provides
    @Singleton
    AlarmManager provideAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Provides
    @Singleton
    AlarmRepo provideAlarmRepo(Context context, AlarmManager alarmManager, Config config) {
        return new AlarmRepoImpl(context, alarmManager, config);
    }
}
