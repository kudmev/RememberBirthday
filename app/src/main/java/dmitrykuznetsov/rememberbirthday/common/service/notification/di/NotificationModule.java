package dmitrykuznetsov.rememberbirthday.common.service.notification.di;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

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
    NotificationManager provideNotificationManager(Context context) {
        return ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
    }

    @Provides
    Notification.Builder provideNotificationBuilder(Context context) {
        return new Notification.Builder(context);
    }

    @Provides
    NotificationInteractor provideNotificationInteractor(PersonRepo personRepo) {
        return new NotificationInteractorImpl(personRepo);
    }
}
