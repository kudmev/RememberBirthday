package dmitrykuznetsov.rememberbirthday.common.support.di;

import android.content.Context;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.support.Convert;
import dmitrykuznetsov.rememberbirthday.common.support.Utils;

/**
 * Created by vernau on 5/24/17.
 */

@Module
public class SupportModule {

    @Provides
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    Utils provideUtils(Context context) {
        return new Utils(context);
    }

    @Provides
    Convert provideConvert() {
        return new Convert();
    }


}
