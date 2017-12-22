package dmitrykuznetsov.rememberbirthday.common.binding;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;


public class BindingAdapters {
    private static final String TAG = Constants.LOG_TAG + BindingAdapters.class.getSimpleName();

    @BindingAdapter({"age"})
    public static void setAge(TextView view, long dateInMillis) {
        int age = getYears(dateInMillis);
        String ageStr = String.valueOf(age);
        view.setText(ageStr);
    }

    @BindingAdapter({"birthday"})
    public static void setBirthday(TextView view, long dateInMillis) {
        if (dateInMillis != 0) {
            LocalDate birthDate = new LocalDate(dateInMillis);
            int age = getYears(dateInMillis);
            birthDate.plusYears(age);

            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM yyyy");
            String dayBirthday = birthDate.toString(formatter);
            view.setText(dayBirthday);
        }
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

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String path) {
        Drawable drawable = Drawable.createFromPath(path);
        imageView.setImageDrawable(drawable);
    }

//    @BindingAdapter({"imageUrl"})
//    public static void loadImage2(ImageView imageView, ObservableField<String> path) {
//        Drawable drawable = Drawable.createFromPath(path.get());
//        imageView.setImageDrawable(drawable);
//    }

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

    @BindingAdapter({"onClick"})
    public static void bindOnClick(View view, final Runnable runnable) {
        view.setOnClickListener(view1 -> runnable.run());
    }

    @BindingAdapter({"bindTouchListener"})
    public static void setListener(View view, View.OnTouchListener listener) {
        view.setOnTouchListener(listener);
    }

}
