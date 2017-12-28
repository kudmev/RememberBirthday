package dmitrykuznetsov.rememberbirthday.common.callback;

/**
 * Created by dmitry on 01.04.17.
 */

public interface ServiceCallback<T> {
    void onSuccess(T t);
    void onFailed(String error);
}
