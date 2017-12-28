package dmitrykuznetsov.rememberbirthday.common.data.model;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;

import dmitrykuznetsov.rememberbirthday.R;

/**
 * Created by dmitry on 11.03.17.
 */

public class Person {
    public final ObservableInt id = new ObservableInt();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> note = new ObservableField<>();
    public final ObservableField<String> bindPhone = new ObservableField<>();
    public final ObservableField<String> pathImage = new ObservableField<>();
    public final ObservableLong dateInMillis = new ObservableLong();

    public Person(int id, String name, String note, String bindPhone, String pathImage, long dateInMillis) {
        this.id.set(id);
        this.name.set(name);
        this.note.set(note);
        this.bindPhone.set(bindPhone);
        this.pathImage.set(pathImage);
        this.dateInMillis.set(dateInMillis);
    }

    public String getMessageIfError(Context context){
        String message = null;

        if (name.get() == null || name.get().equals("")) {
            message = context.getString(R.string.error_name);
        }

        if (dateInMillis.get() == 0) {
            message = context.getString(R.string.error_date_millis);
        }
        return message;
    }
}
