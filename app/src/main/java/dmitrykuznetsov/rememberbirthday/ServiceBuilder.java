package dmitrykuznetsov.rememberbirthday;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dmitrykuznetsov.rememberbirthday.common.service.notification.ServiceSendNotification;
import dmitrykuznetsov.rememberbirthday.common.service.notification.di.ServiceSendModule;

/**
 * Created by vernau on 24.08.17.
 */

@Module
public abstract class ServiceBuilder {

    @ContributesAndroidInjector(modules = ServiceSendModule.class)
    abstract ServiceSendNotification bindTokenService();

}
