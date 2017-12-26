package dmitrykuznetsov.rememberbirthday.common.background.di;



import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.common.background.AddTokenInterceptor;
import dmitrykuznetsov.rememberbirthday.common.background.ReceiveTokenInterceptor;
import dmitrykuznetsov.rememberbirthday.common.background.RetrofitInitializer;
import dmitrykuznetsov.rememberbirthday.common.error.NullOnEmptyConverterFactory;
import dmitrykuznetsov.rememberbirthday.common.error.RxErrorHandlingCallAdapterFactory;
import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vernau on 5/24/17.
 */

@Module
public class NetworkModule {

    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }

    @Provides
    RetrofitInitializer provideRetrofitInitializer(OkHttpClient.Builder httpClient, Retrofit.Builder builder, AddTokenInterceptor addTokenInterceptor, ReceiveTokenInterceptor receiveTokenInterceptor) {
        return new RetrofitInitializer(httpClient, builder, addTokenInterceptor, receiveTokenInterceptor);
    }

    @Provides
    AddTokenInterceptor provideAddTokenInterceptor(Config config) {
        return new AddTokenInterceptor(config);
    }

    @Provides
    ReceiveTokenInterceptor provideReceiveTokenInterceptor(Config config) {
        return new ReceiveTokenInterceptor(config);
    }


}
