package dmitrykuznetsov.rememberbirthday.features.birthday.add.repo;

import android.net.Uri;

/**
 * Created by dmitry on 14.05.17.
 */

public interface PhoneRetriever {
    String getPhone(Uri uri);
}
