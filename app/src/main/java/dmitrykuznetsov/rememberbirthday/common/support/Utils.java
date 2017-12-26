package dmitrykuznetsov.rememberbirthday.common.support;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import dmitrykuznetsov.rememberbirthday.R;

public class Utils {

    private Context context;

    public Utils(Context context) {
        this.context = context;
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
        messageView.setPadding(10, 7, 10, 7);

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
