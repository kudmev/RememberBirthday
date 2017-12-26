package dmitrykuznetsov.rememberbirthday.common.error;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by vernau on 3/31/17.
 */

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @SuppressWarnings("unchecked")
    @Override
    public CallAdapter get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper<R, T> implements CallAdapter<R, Observable> {
        private final Retrofit retrofit;
        private final CallAdapter<R, T> wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<R, T> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Observable adapt(Call<R> call) {
            return ((Observable) wrapped.adapt(call)).onErrorResumeNext(throwable -> {
                return Observable.error(convertToRetrofitException((Throwable) throwable));
            });
        }

        private RetrofitException convertToRetrofitException(Throwable throwable) {
            // Non-200 http error
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                return RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit);
            }
            // A network error
            if (throwable instanceof IOException) {
                return RetrofitException.networkError((IOException) throwable);
            }
            // Unknown error
            return RetrofitException.unexpectedError(throwable);
        }
    }
}
