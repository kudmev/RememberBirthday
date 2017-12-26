package dmitrykuznetsov.rememberbirthday.common.error;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import dmitrykuznetsov.rememberbirthday.common.service.TokenResponse;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by vernau on 7/31/17.
 */

public class NullOnEmptyConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return (Converter<ResponseBody, Object>) body -> {
            if (body.contentLength() == 0)  {
                return new TokenResponse();
            }
            return delegate.convert(body);
        };
    }
}
