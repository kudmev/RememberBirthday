package dmitrykuznetsov.rememberbirthday.common.data.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepoImpl;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetriever;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetrieverImpl;

/**
 * Created by Alena on 28.12.2017.
 */
@Module
public class RepoModule {

    @Provides
    @Singleton
    PersonRepo providePersonRepo(){
        return new PersonRepoImpl();
    }

    @Provides
    @Singleton
    PhoneRetriever providePhoneRetriever(Context context) {
        return new PhoneRetrieverImpl(context);
    }

}
