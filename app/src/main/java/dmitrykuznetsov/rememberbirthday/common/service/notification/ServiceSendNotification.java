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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.data.repo.PersonRepo;
import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.features.birthday.settings.SettingsFragment;
import dmitrykuznetsov.rememberbirthday.old.MainActivity;
import dmitrykuznetsov.rememberbirthday.old.MyHelperClass;

/**
 * Created by Dmitry Kuznetsov on 26.07.2015.
 */
public class ServiceSendNotification extends DaggerIntentService {
//    private Cursor c;
//    private int uid;
//    private AlarmManager alarmManager;
//    private PendingIntent pendingIntent;
//    private String ALARM_ACTION = AlarmNotificationReceiver.ACTION_SEND_NOTIFICATION;

//    @Override
//    public void onCreate() {
//        super.onCreate();
////        Toast.makeText(getApplicationContext(), "Запустился сервис уведомлений",
////                Toast.LENGTH_SHORT).show();
//    }

    @Inject
    PersonRepo personRepo;

    private long alarmMillis;

    @Override
    protected void onHandleIntent(Intent intent) {
        alarmMillis = intent.getLongExtra(Constants.ALARM_TIME, 0);
        personRepo.getPersons("")
                .subscribe(this::onSuccess, this::onError);
//        SharedPreferences mySettings = getSharedPreferences(MainActivity.APP_SETTINGS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = mySettings.edit();
//        Calendar calendar = Calendar.getInstance();
//        int curDay = calendar.get(Calendar.DAY_OF_YEAR);
//        editor.putInt(MainActivity.SETTINGS_DATE_NOTIFICATION, curDay);
//        editor.apply();
//
    }

    private void onSuccess(List<PersonData> persons) {
        DateTime alarmTime = new DateTime(alarmMillis);
        int alarmTimeDayOfYear = alarmTime.getDayOfYear();
        int nowDayOfYear = DateTime.now().getDayOfYear();

        List<PersonData> birthdaysTodayOrEarlier = new ArrayList<>();
        for (PersonData personData : persons) {
            int personDayOfYear = new DateTime(personData.getDateInMillis()).getDayOfYear();
            if (alarmTimeDayOfYear <= personDayOfYear && personDayOfYear <= nowDayOfYear) {
                birthdaysTodayOrEarlier.add(personData);
            }
        }

        for (PersonData personData: birthdaysTodayOrEarlier) {
            Log.d("ServiceSendNotification", personData.getName());
            buildNotification(getApplicationContext(), personData);
        }
    }

    private void onError(Throwable throwable) {
    }


//
//    private Cursor getUsersWhichBirthdayToday(int uid) {
//        ContentResolver cr = getContentResolver();
//        Log.d(" getCursSendNotifyToday", "Yes");
//
//        String where = RememberContentProvider.UID + "=" + uid;
//        return cr.query(RememberContentProvider.CONTENT_URI, null, where, null, null);
//    }
//
//    private Cursor getUsersBirthdayWhichAlreadyPassed(long milliseconds) {
//        ContentResolver cr = getContentResolver();
//        Log.d("getCursorDateRefresh", "Yes");
//
//        String where = "";
//        if (MyHelperClass.isUserBirthdayToday(milliseconds)) {
//            where = RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS + "<" + milliseconds;
//            Log.d("where", where);
//        } else {
//            where = RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS + "<=" + milliseconds;
//            Log.d("where", where);
//        }
//
//        Cursor cursor = cr.query(RememberContentProvider.CONTENT_URI, null, where, null, null);
//        Log.d("size cursor", String.valueOf(cursor.getCount()));
//        return cursor;
//    }

    public ServiceSendNotification() {
        super("ServiceSendNotification");
    }

    public ServiceSendNotification(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void buildNotification(Context context, PersonData personData) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, personData.getId(),
                intent, 0);

        String dateBirthday;
        if (MyHelperClass.isUserBirthdayToday(personData.getDateInMillis())) {
            dateBirthday = " сегодня отмечает ДР";
        } else {
            DateTime userTime = new DateTime(personData.getDateInMillis());
            dateBirthday = " " + userTime.getDayOfMonth() + "-" + userTime.getMonthOfYear() + " отмечал ДР";
        }

        Bitmap userBitmap = BitmapFactory.decodeFile(personData.getPathImage());

        builder.setSmallIcon(R.mipmap.logo);
        if (userBitmap != null) {
            builder.setLargeIcon(userBitmap);
        }

        builder.setContentTitle("Напоминание").setContentText(personData.getName() + dateBirthday)
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

        //NOTIFICATION_REF++;
        Notification notification = builder.build();
        notificationManager.notify(personData.getId(), notification);

    }

//    private void buildNotification(Context context, Cursor c, int uid, long dateMillis) {
//
//        NotificationManager notificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification.Builder builder = new Notification.Builder(context);
//
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, uid,
//                intent, 0);
//
//        while (c.moveToNext()) {
//
//            String name = c.getString(c.getColumnIndex(RememberContentProvider.NAME));
//            String path = c.getString(c.getColumnIndex(RememberContentProvider.PATHIMAGE));
//
//            String dateBirthday;
//            if (MyHelperClass.isUserBirthdayToday(dateMillis)) {
//                dateBirthday = " сегодня отмечает ДР";
//            } else {
//                dateBirthday = " " + c.getString(c.getColumnIndex("date_birthday")) + " отмечал ДР";
//            }
//
//            Bitmap userBitmap = MyHelperClass.loadImageFromStorage(path, uid);
//
//
//            builder.setSmallIcon(R.mipmap.logo);
//            if (userBitmap != null)
//                builder.setLargeIcon(userBitmap);
//
//            builder.setContentTitle("Напоминание").setContentText(name + dateBirthday)
//                    .setSound(null)
//                    .setLights(Color.WHITE, 0, 1)
//                    .setContentIntent(pendingIntent).setAutoCancel(true);
//
//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            Boolean soundNotification = sharedPreferences.getBoolean(SettingsFragment.pref_sync, false);
//            Boolean vibrateNotification = sharedPreferences.getBoolean(SettingsFragment.pref_vibrate, false);
//
//            String alarms = sharedPreferences.getString(SettingsFragment.pref_ringtone, "default ringtone");
//
//            if (soundNotification) {
//                builder.setDefaults(Notification.DEFAULT_LIGHTS);
//                RingtoneManager.getRingtone(this, Uri.parse(alarms)).play();
//            }
//
//            if (vibrateNotification) {
//                builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
//                builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
//            }
//
//
//            //NOTIFICATION_REF++;
//            Notification notification = builder.build();
//            notificationManager.notify(uid, notification);
//        }
//    }

//    private void updateListBirthdaysOfUsers(Cursor c) {
//        while (c.moveToNext()) {
//
//            int item_id = c.getInt(c.getColumnIndex(RememberContentProvider.UID));
//            Log.d("updateDate with UID", String.valueOf(item_id));
//            long timeUser = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
//            int age = c.getInt(c.getColumnIndex(RememberContentProvider.AGE_PERSON));
//
//            Calendar calendar = Calendar.getInstance();
//            int curYear = calendar.get(Calendar.YEAR);
//            int curDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
//            Log.d("curYear", String.valueOf(curYear));
//
//            calendar.setTimeInMillis(timeUser);
//            int userYear = calendar.get(Calendar.YEAR);
//            int userDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
//            Log.d("userYear", String.valueOf(userYear));
//            if (curDayOfYear > userDayOfYear) {
//                calendar.add(Calendar.YEAR, curYear - userYear + 1);
//            } else {
//                calendar.add(Calendar.YEAR, curYear - userYear);
//            }
//
//            long setTime = calendar.getTimeInMillis();
//
//            Log.d("timeUser", String.valueOf(timeUser));
//            Log.d("setTime", String.valueOf(setTime));
//
//            //Locale myLocale = new Locale("ru","RU");
//
//            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
//            String dateString = formatter.format(new Date(setTime));
//
//            ContentResolver cr = getContentResolver();
//            ContentValues updatedValues = new ContentValues();
//            updatedValues.put(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS, setTime);
//            updatedValues.put(RememberContentProvider.DATE_BIRTHDAY, dateString);
//            if (curDayOfYear > userDayOfYear) {
//                updatedValues.put(RememberContentProvider.AGE_PERSON, age + curYear - userYear + 1);
//            } else {
//                updatedValues.put(RememberContentProvider.AGE_PERSON, age + curYear - userYear);
//            }
//
//            String where1 = RememberContentProvider.UID + "=" + String.valueOf(item_id);
//            cr.update(RememberContentProvider.CONTENT_URI, updatedValues, where1, null);
//
//            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            int alarmType = AlarmManager.RTC_WAKEUP;
//
//            Intent intentToFire = new Intent(ALARM_ACTION);
//            intentToFire.putExtra("milliseconds", setTime);
//            intentToFire.putExtra("rowId", item_id);
//
//            pendingIntent = PendingIntent.getBroadcast(this, item_id, intentToFire, pendingIntent.FLAG_CANCEL_CURRENT);
//            Log.d("pendingintent", pendingIntent.toString());
//            alarmManager.set(alarmType, setTime, pendingIntent);
//        }
//    }


}