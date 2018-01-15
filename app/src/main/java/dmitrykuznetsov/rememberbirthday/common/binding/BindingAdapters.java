package dmitrykuznetsov.rememberbirthday.common.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;


public class BindingAdapters {
    private static final String TAG = Constants.LOG_TAG + BindingAdapters.class.getSimpleName();

    @BindingAdapter({"onClick"})
    public static void bindOnClick(View view, final Runnable runnable) {
        view.setOnClickListener(view1 -> runnable.run());
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String path) {
        if (path != null && !path.equals("")) {
            Drawable drawable = Drawable.createFromPath(path);
            imageView.setImageDrawable(drawable);
        } else {
            imageView.setImageDrawable(null);
        }
    }

    @BindingAdapter({"bindTouchListener"})
    public static void setListener(View view, View.OnTouchListener listener) {
        view.setOnTouchListener(listener);
    }

    @BindingAdapter({"startAnimation"})
    public static void startAnimation(final View view, Animation animation) {
        final AnimationSet animationSet = (AnimationSet) animation;

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(animation);
    }

    @BindingAdapter({"drawable"})
    public static void setDrawable(ImageView imageView, int resourceId) {
        Context context = imageView.getContext();
        Drawable drawable;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getResources().getDrawable(resourceId, context.getTheme());
        } else {
            drawable = context.getResources().getDrawable(resourceId);
        }
        imageView.setImageDrawable(drawable);
    }

    @BindingAdapter({"drawable"})
    public static void setDrawable(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }


    @BindingAdapter({"age"})
    public static void setAge(TextView view, long dateInMillis) {
        int age = getYears(dateInMillis);
        String ageStr = String.valueOf(age);
        view.setText(ageStr);
    }

    @BindingAdapter({"birthday_main"})
    public static void setBirthdayMain(TextView view, long dateInMillis) {
            LocalDate birthDate = new LocalDate(dateInMillis);
            String dayBirthday;
            if (birthDate.getDayOfYear() == LocalDate.now().getDayOfYear()) {
                dayBirthday = view.getContext().getString(R.string.birthday_today);
            } else {
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM");
                dayBirthday = birthDate.toString(formatter);
            }
            view.setText(dayBirthday);
    }

    @BindingAdapter({"birthday"})
    public static void setBirthday(TextView view, long dateInMillis) {
            LocalDate birthDate = new LocalDate(dateInMillis);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM yyyy");
            String dayBirthday = birthDate.toString(formatter);
            view.setText(dayBirthday);
    }

    @BindingAdapter({"birthdayAndIcon"})
    public static void setBirthdayAndIcon(TextView view, long dateInMillis) {
        if (dateInMillis != 0) {
            setBirthday(view, dateInMillis);
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.calendar_check, 0);
        }
    }

    private static int getYears(long dateInMillis) {
        LocalDate birthDate = new LocalDate(dateInMillis);
        LocalDate now = new LocalDate();
        Years years = Years.yearsBetween(birthDate, now);
        return years.getYears();
    }

    @BindingAdapter({"visibility_field"})
    public static void setBirthdayAndIcon(View view, String value) {
        if (value == null || value.equals("")) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
