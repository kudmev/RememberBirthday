package dmitrykuznetsov.rememberbirthday.common.support;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

import dmitrykuznetsov.rememberbirthday.R;

public class Utils {

    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public String getAgeSuffix(int age, int flag) {
        String ageSuffix;

        if (age % 10 >= 2 && age % 10 <= 4 && (age < 5 || age > 20)) {
            ageSuffix = context.getString(R.string.year_rus);
        } else {
            if (age % 10 != 1) {
                ageSuffix = context.getString(R.string.years);
            } else {
                ageSuffix = context.getString(R.string.year);
            }
        }
        if (flag == 0) {
            return age + "\n" + ageSuffix;
        } else {
            return age + " " + ageSuffix;
        }

    }

    public boolean isUserBirthdayToday(long milliseconds) {
        DateTime now = DateTime.now();
        DateTime userTime = new DateTime(milliseconds);
        return now.getDayOfYear() == userTime.getDayOfYear();
    }

    public String toStr(Object object) {
        return object == null ? null : object.toString();
    }

    public static void showToastBar(Activity activity, String value) {
        Context context = activity.getApplicationContext();
        Resources resources = context.getResources();

        Toast toast = Toast.makeText(context, value, Toast.LENGTH_SHORT);
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(resources.getColor(R.color.colorPrimary));

        TextView messageView = linearLayout.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        messageView.setTextColor(resources.getColor(R.color.colorWhite));
        messageView.setPadding(13, 5, 13, 5);

        toast.show();
    }

    public <T> List<T> getListFromJsonRaw(int rawId, Class<T> clazz) {
        InputStream in = context.getResources().openRawResource(rawId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        Type listType = new ListParameterizedType(clazz);
        List<T> list = new Gson().fromJson(reader, listType);
        return list;
    }

}
