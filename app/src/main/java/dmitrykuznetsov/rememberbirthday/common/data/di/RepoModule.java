package dmitrykuznetsov.rememberbirthday.common.data.di;

import android.content.ContentResolver;
import android.content.Context;
import android.content.UriMatcher;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepoImpl;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetriever;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PhoneRetrieverImpl;
import dmitrykuznetsov.rememberbirthday.common.data.RememberContentProvider;


@Module
public class RepoModule {

    @Provides
    @Singleton
    ContentResolver provideContentResolver(Context context) {
        return context.getContentResolver();
    }

    @Provides
    @Singleton
    @Named("uri_single_row")
    UriMatcher provideUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RememberContentProvider.AUTHORITY, "/#", RememberContentProvider.SINGLE_BIRTHDAY);
        return uriMatcher;
    }

    @Provides
    @Singleton
    PersonRepo providePersonRepo(ContentResolver contentResolver, Context context) {
        return new PersonRepoImpl(contentResolver, context);
    }

    @Provides
    @Singleton
    PhoneRetriever providePhoneRetriever(Context context) {
        return new PhoneRetrieverImpl(context);
    }

}
