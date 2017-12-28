package dmitrykuznetsov.rememberbirthday.common.data.repo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by dmitry on 14.05.17.
 */

public class PhoneRetrieverImpl implements PhoneRetriever {

    public Context context;

    public PhoneRetrieverImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getPhone(Uri contactData) {
        Cursor cursor = context.getContentResolver().query(contactData, null, null,
                null, null);

        String stringNumber = null;
        if (cursor != null && cursor.getColumnCount() != 0) {
            cursor.moveToNext();
            int columnIndex_ID = cursor
                    .getColumnIndex(ContactsContract.Contacts._ID);
            String contactID = cursor.getString(columnIndex_ID);

            int columnIndex_HASPHONENUMBER = cursor
                    .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            String stringHasPhoneNumber = cursor
                    .getString(columnIndex_HASPHONENUMBER);

            if (stringHasPhoneNumber.equalsIgnoreCase("1")) {
                Cursor cursorNum = context.getContentResolver()
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + "=" + contactID, null, null);
                if (cursorNum != null) {
                    cursorNum.moveToNext();
                    int columnIndex_number = cursorNum
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    stringNumber = cursorNum
                            .getString(columnIndex_number);
                    cursorNum.close();
                }
            }
            cursor.close();
        }
        return stringNumber;
    }
}
