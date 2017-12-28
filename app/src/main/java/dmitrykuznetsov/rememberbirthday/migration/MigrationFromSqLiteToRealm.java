package dmitrykuznetsov.rememberbirthday.migration;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dmitrykuznetsov.rememberbirthday.old.RememberContentProvider;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import io.realm.Realm;

/**
 * Created by dmitry on 09.05.17.
 */

public class MigrationFromSqLiteToRealm {

    private static final String TAG = Constants.LOG_TAG + MigrationFromSqLiteToRealm.class.getSimpleName();

    public static void migration(Context context) {
        final ContentResolver cr = context.getContentResolver();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Realm realm = Realm.getDefaultInstance();

                Cursor c = cr.query(RememberContentProvider.CONTENT_URI, null, null, null, null);
                List<PersonData> dataList = new ArrayList<>();
                if (c != null) {
                    while (c.moveToNext()) {
                        int userId = c.getInt(c.getColumnIndex(RememberContentProvider.UID));
                        String name = c.getString(c.getColumnIndex(RememberContentProvider.NAME));
                        String note = c.getString(c.getColumnIndex(RememberContentProvider.NOTE));
                        String pathImage = c.getString(c.getColumnIndex(RememberContentProvider.PATHIMAGE));
                        long dateInMillis = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
                        String bindPhone = c.getString(c.getColumnIndex(RememberContentProvider.PHONE_NUMBER));

                        PersonData personData = new PersonData(userId, name, note, bindPhone, pathImage, dateInMillis, null);
                        dataList.add(personData);
                    }
                    c.close();
                } else {
                    Log.d(TAG, "Migration not available");
                }

                realm.beginTransaction();
                realm.copyToRealm(dataList);
                realm.commitTransaction();
                return null;
            }

        }.execute();
    }
}
