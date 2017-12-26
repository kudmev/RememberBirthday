package dmitrykuznetsov.rememberbirthday.features.birthday.add.di;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.PhoneRetriever;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.repo.PhoneRetrieverImpl;

/**
 * Created by Alena on 26.12.2017.
 */
@Module
public class PhoneRetrieverModule {

    @Provides
    PhoneRetriever providePhoneRetriever(Context context) {
        return new PhoneRetrieverImpl(context);
    }
}
