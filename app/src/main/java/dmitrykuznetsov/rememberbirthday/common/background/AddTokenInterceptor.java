package dmitrykuznetsov.rememberbirthday.common.background;

import java.io.IOException;

import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vernau on 5/30/17.
 */

public class AddTokenInterceptor implements Interceptor {

    Config config;

    public AddTokenInterceptor(Config config) {
        this.config = config;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();

        String token = config.getAsString(Constants.X_AUTH_TOKEN);
        if (token != null) {
            requestBuilder
                    .header(Constants.X_AUTH_TOKEN, token)
                    .method(original.method(), original.body());
        }
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
