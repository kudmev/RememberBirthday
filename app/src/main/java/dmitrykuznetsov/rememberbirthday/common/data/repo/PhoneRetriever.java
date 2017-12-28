package dmitrykuznetsov.rememberbirthday.common.data.repo;

import android.net.Uri;

/**
 * Created by dmitry on 14.05.17.
 */

public interface PhoneRetriever {
    String getPhone(Uri uri);
}
