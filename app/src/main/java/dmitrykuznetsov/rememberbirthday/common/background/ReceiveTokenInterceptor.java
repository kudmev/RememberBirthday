package dmitrykuznetsov.rememberbirthday.common.background;



import java.io.IOException;
import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * Created by vernau on 5/30/17.
 */

public class ReceiveTokenInterceptor implements Interceptor {

    private Config config;

    public ReceiveTokenInterceptor(Config config) {
        this.config = config;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        List<String> headers = originalResponse.headers(Constants.X_AUTH_TOKEN);

        if (headers != null && !headers.isEmpty()) {
            for (String header : headers) {
                if (header != null) {
                    config.set(Constants.X_AUTH_TOKEN, header);
                }
            }
        }
        return originalResponse;
    }
}
