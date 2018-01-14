package dmitrykuznetsov.rememberbirthday;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dmitrykuznetsov.rememberbirthday.common.service.notification.NotificationService;
import dmitrykuznetsov.rememberbirthday.common.service.notification.di.NotificationModule;

/**
 * Created by vernau on 24.08.17.
 */

@Module
public abstract class ServiceBuilder {

    @ContributesAndroidInjector(modules = NotificationModule.class)
    abstract NotificationService bindTokenService();

}
