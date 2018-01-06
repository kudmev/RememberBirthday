package dmitrykuznetsov.rememberbirthday.common.data.di;

import android.content.ContentResolver;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepoImpl;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetriever;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetrieverImpl;


@Module
public class RepoModule {

    @Provides
    @Singleton
    ContentResolver provideContentResolver(Context context){
        return context.getContentResolver();
    }

    @Provides
    @Singleton
    PersonRepo providePersonRepo(ContentResolver contentResolver){
        return new PersonRepoImpl(contentResolver);
    }

    @Provides
    @Singleton
    PhoneRetriever providePhoneRetriever(Context context) {
        return new PhoneRetrieverImpl(context);
    }

}
