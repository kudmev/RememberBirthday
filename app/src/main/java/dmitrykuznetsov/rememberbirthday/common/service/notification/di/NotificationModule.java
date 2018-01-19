package dmitrykuznetsov.rememberbirthday.common.service.notification.di;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.service.notification.interactor.NotificationInteractor;
import dmitrykuznetsov.rememberbirthday.common.service.notification.interactor.NotificationInteractorImpl;

/**
 * Created by dmitry on 1/12/18.
 */

@Module
public class NotificationModule {

    @Provides
    NotificationInteractor provideNotificationInteractor(PersonRepo personRepo) {
        return new NotificationInteractorImpl(personRepo);
    }

    @Provides
    NotificationManager provideNotificationManager(Context context) {
        return ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
    }


}
