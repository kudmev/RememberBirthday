package dmitrykuznetsov.rememberbirthday;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import dmitrykuznetsov.rememberbirthday.common.adapter.di.RecyclerModule;
import dmitrykuznetsov.rememberbirthday.common.background.di.NetworkModule;
import dmitrykuznetsov.rememberbirthday.common.support.di.StorageModule;
import dmitrykuznetsov.rememberbirthday.common.support.di.SupportModule;

/**
 * Created by vernau on 5/23/17.
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AppModule.class,
        StorageModule.class,
        SupportModule.class,
        NetworkModule.class,
        RecyclerModule.class,
        ActivityBuilder.class,
        FragmentBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(App app);
}
