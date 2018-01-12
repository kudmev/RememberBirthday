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
import dmitrykuznetsov.rememberbirthday.old.RememberContentProvider;


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
    UriMatcher provideUriMatcher(Context context) {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RememberContentProvider.AUTHORITY, "/#", RememberContentProvider.SINGLE_ROW);
        return uriMatcher;
    }

    @Provides
    @Singleton
    PersonRepo providePersonRepo(ContentResolver contentResolver, @Named("uri_single_row") UriMatcher uriMatcher) {
        return new PersonRepoImpl(contentResolver, uriMatcher);
    }

    @Provides
    @Singleton
    PhoneRetriever providePhoneRetriever(Context context) {
        return new PhoneRetrieverImpl(context);
    }

}
