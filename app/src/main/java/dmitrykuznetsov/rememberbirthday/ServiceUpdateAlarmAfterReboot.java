package dmitrykuznetsov.rememberbirthday;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmitry Kuznetsov on 26.07.2015.
 */
public class ServiceUpdateAlarmAfterReboot extends IntentService {


    private Cursor c;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private String ALARM_ACTION=AlarmNotificationReceiver.ACTION_SEND_NOTIFICATION;
    private Intent intentToFire=new Intent(ALARM_ACTION);
    private int alarmType = AlarmManager.RTC_WAKEUP;

    @Override
    public void onCreate() {
        super.onCreate();

        alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);


    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("ServiceUpdateAfterRebo", "start");
//        Toast.makeText(getApplicationContext(), "Запустился сервис обновления будильников",
//                Toast.LENGTH_SHORT).show();
        c=getAllUsers();

        if (c.getCount()!=0) {
//            Toast.makeText(getApplicationContext(), "Курсор обновления будильников",
//                    Toast.LENGTH_SHORT).show();
            while (c.moveToNext()) {
                int uid = c.getInt(c.getColumnIndex(RememberContentProvider.UID));
                long timeUser = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));

                intentToFire.putExtra("milliseconds", timeUser);
                intentToFire.putExtra("rowId", uid);

                pendingIntent= PendingIntent.getBroadcast(this, uid, intentToFire, pendingIntent.FLAG_CANCEL_CURRENT);
                Log.d("Create pendingintent", pendingIntent.toString());
                alarmManager.set(alarmType, timeUser, pendingIntent);
            }
        }


    }

    public ServiceUpdateAlarmAfterReboot() {
        super("ServiceUpdateAlarmAfterReboot");
    }

    public ServiceUpdateAlarmAfterReboot(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Cursor getAllUsers() {
        ContentResolver cr=getContentResolver();
        String[] projection=new String[]{RememberContentProvider.UID, RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS};

        return cr.query(RememberContentProvider.CONTENT_URI, projection, null, null, null);
    }

    private void updateListBirthdaysOfUsers(Cursor c) {
        while (c.moveToNext()) {
                int item_id = c.getInt(c.getColumnIndex(RememberContentProvider.UID));
                long timeUser = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
                int age=c.getInt(c.getColumnIndex(RememberContentProvider.AGE_PERSON));

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timeUser);
                calendar.add(Calendar.YEAR, 1);
                long setTime = calendar.getTimeInMillis();

                //Locale myLocale = new Locale("ru","RU");

                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                String dateString = formatter.format(new Date(setTime));

                ContentResolver cr = getContentResolver();
                ContentValues updatedValues = new ContentValues();
                updatedValues.put(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS, setTime);
                updatedValues.put(RememberContentProvider.DATE_BIRTHDAY, dateString);
                updatedValues.put(RememberContentProvider.AGE_PERSON, age+1);
                String where1 = RememberContentProvider.UID + "=" + String.valueOf(item_id);
                cr.update(RememberContentProvider.CONTENT_URI, updatedValues, where1, null);


        }
    }
}