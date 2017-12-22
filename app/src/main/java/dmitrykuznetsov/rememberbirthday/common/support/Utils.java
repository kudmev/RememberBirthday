package dmitrykuznetsov.rememberbirthday.common.support;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import dmitrykuznetsov.rememberbirthday.R;

public class Utils {

    public static String toStr(Object object) {
        return object == null ? null : object.toString();
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void showToastBar(Activity activity, String value) {
        Context context = activity.getApplicationContext();
        Resources resources = context.getResources();

        Toast toast = Toast.makeText(context, value, Toast.LENGTH_SHORT);
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(resources.getColor(R.color.colorPrimary));

        TextView messageView = (TextView) linearLayout.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        messageView.setTextColor(resources.getColor(R.color.colorWhite));
        messageView.setPadding(10, 7, 10, 7);

        toast.show();
    }

}
