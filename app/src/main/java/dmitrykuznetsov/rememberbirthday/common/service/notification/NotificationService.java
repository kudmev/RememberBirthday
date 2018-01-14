package dmitrykuznetsov.rememberbirthday.common.service.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.service.notification.interactor.NotificationInteractor;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.common.support.Utils;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.DetailBirthdayActivity;
import dmitrykuznetsov.rememberbirthday.features.settings.SettingsFragment;

/**
 * Created by Dmitry Kuznetsov on 26.07.2015.
 */
public class NotificationService extends DaggerIntentService {

    private final static String CLASS_NAME = NotificationService.class.getClass().getSimpleName();

    @Inject
    NotificationInteractor notificationInteractor;

    @Inject
    NotificationManager notificationManager;

    @Inject
    Notification.Builder builder;

    @Inject
    Utils utils;

    public NotificationService() {
        super(CLASS_NAME);
    }

    public NotificationService(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long alarmMillis = intent.getLongExtra(Constants.ALARM_TIME, 0);
        notificationInteractor.getPersonsWaitNotification(alarmMillis)
                .subscribe(this::onSuccess, this::onError);
    }

    private void onSuccess(List<PersonData> personsWaitNotification) {
        for (PersonData personData : personsWaitNotification) {
            buildNotification(getApplicationContext(), personData);
        }
    }

    private void onError(Throwable throwable) {
        Log.d(CLASS_NAME, throwable.getMessage());
    }

    private void buildNotification(Context context, PersonData personData) {
        Intent intent = new Intent(context, DetailBirthdayActivity.class);
        intent.putExtra(Constants.PERSON_DATA, personData);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, personData.getId(),
                intent, 0);

        Bitmap userBitmap = BitmapFactory.decodeFile(personData.getPathImage());

        builder.setSmallIcon(R.mipmap.logo);
        if (userBitmap != null) {
            builder.setLargeIcon(userBitmap);
        }

        String contentText;
        if (utils.isUserBirthdayToday(personData.getDateInMillis())) {
            contentText = context.getString(R.string.notification_person_today_birthday, personData.getName());
        } else {
            DateTime userTime = new DateTime(personData.getDateInMillis());
            String dayPrint = String.valueOf(userTime.getDayOfMonth());
            int month = userTime.getMonthOfYear();
            String monthPrint = month < 10 ? String.valueOf(month) : String.valueOf("0" + month);
            contentText = context.getString(R.string.notification_person_date_birthday, personData.getName(), dayPrint, monthPrint);
        }

        String contentTitle = context.getString(R.string.notification_title);

        builder.setContentTitle(contentTitle).setContentText(contentText)
                .setSound(null)
                .setLights(Color.WHITE, 0, 1)
                .setContentIntent(pendingIntent).setAutoCancel(true);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean soundNotification = sharedPreferences.getBoolean(SettingsFragment.pref_sync, false);
        Boolean vibrateNotification = sharedPreferences.getBoolean(SettingsFragment.pref_vibrate, false);

        String alarms = sharedPreferences.getString(SettingsFragment.pref_ringtone, "default ringtone");

        if (soundNotification) {
            builder.setDefaults(Notification.DEFAULT_LIGHTS);
            RingtoneManager.getRingtone(this, Uri.parse(alarms)).play();
        }

        if (vibrateNotification) {
            builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
            builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        }

        Notification notification = builder.build();
        notificationManager.notify(personData.getId(), notification);
    }
}