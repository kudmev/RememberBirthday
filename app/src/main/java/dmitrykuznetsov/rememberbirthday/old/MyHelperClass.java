package dmitrykuznetsov.rememberbirthday.old;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

import dmitrykuznetsov.rememberbirthday.R;

/**
 * Created by Dmitry Kuznetsov on 31.01.2016.
 */
public class MyHelperClass {
    public static final String TAG = "myLogs";

    public static String getAgeSuffix(int age, Context ctx, int flag) {
        String ageSuffix;

        if (age % 10 >= 2 && age % 10 <= 4 && (age < 5 || age > 20)) {
            ageSuffix = ctx.getString(R.string.age_person_goda);
        } else {
            if (age % 10 != 1) {
                ageSuffix = ctx.getString(R.string.age_person_let);
            } else {
                ageSuffix = ctx.getString(R.string.age_person_god);
            }
        }
        if (flag == 0) {
            return age + "\n" + ageSuffix;
        } else {
            return age + " " + ageSuffix;
        }

    }

    public static boolean isUserBirthdayToday(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        Calendar userCalendar = Calendar.getInstance();
        userCalendar.setTimeInMillis(milliseconds);

        if (calendar.get(Calendar.DAY_OF_YEAR) == userCalendar.get(Calendar.DAY_OF_YEAR)) {
            return true;
        } else {
            return false;
        }
    }


    public static Bitmap loadImageFromStorage(String path, int position) {
        Bitmap b;
        try {
            File f = new File(path, position + ".jpg");
            b = BitmapFactory.decodeStream(new FileInputStream(f));

        } catch (FileNotFoundException e) {
            b = null;
            e.printStackTrace();
        }
        return b;
    }
}
