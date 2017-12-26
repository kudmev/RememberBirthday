package dmitrykuznetsov.rememberbirthday.common.background;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by vernau on 4/17/17.
 */

public class RetrofitInitializer {

    private OkHttpClient.Builder httpClient;
    private Retrofit.Builder builder;
    private AddTokenInterceptor addTokenInterceptor;
    private ReceiveTokenInterceptor receiveTokenInterceptor;

    public RetrofitInitializer(OkHttpClient.Builder httpClient, Retrofit.Builder builder, AddTokenInterceptor addTokenInterceptor, ReceiveTokenInterceptor receiveTokenInterceptor) {
        this.httpClient = httpClient;
        this.builder = builder;
        this.addTokenInterceptor = addTokenInterceptor;
        this.receiveTokenInterceptor = receiveTokenInterceptor;
    }

    public <S> S createService(Class<S> serviceClass) {
        httpClient.addInterceptor(addTokenInterceptor);
        httpClient.addInterceptor(receiveTokenInterceptor);

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

}
