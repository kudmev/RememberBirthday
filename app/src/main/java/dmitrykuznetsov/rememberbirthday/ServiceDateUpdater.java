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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmitry Kuznetsov on 26.07.2015.
 */
public class ServiceDateUpdater extends IntentService {


    private Cursor c;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        String ALARM_ACTION = AlarmReceiver.ACTION_REFRESH_DATA;
        Intent intentToFire = new Intent(ALARM_ACTION);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intentToFire, 0);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        c = getUsersBirthdayWhichAlreadyTodayOrPassed();
        if (c != null) {
            updateListBirthdaysOfUsers(c);
        }

    }

    public ServiceDateUpdater() {
        super("ServiceDateUpdater");
    }

    public ServiceDateUpdater(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Cursor getUsersBirthdayWhichAlreadyTodayOrPassed() {
        ContentResolver cr = getContentResolver();

        long time = System.currentTimeMillis();
        String where = RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS + "<" + (time - (23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000));
        return cr.query(RememberContentProvider.CONTENT_URI, null, where, null, null);
    }

    private void updateListBirthdaysOfUsers(Cursor c) {
        while (c.moveToNext()) {
            int item_id = c.getInt(c.getColumnIndex(RememberContentProvider.UID));
            long timeUser = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
            int age = c.getInt(c.getColumnIndex(RememberContentProvider.AGE_PERSON));

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
            updatedValues.put(RememberContentProvider.AGE_PERSON, age + 1);
            String where1 = RememberContentProvider.UID + "=" + String.valueOf(item_id);
            cr.update(RememberContentProvider.CONTENT_URI, updatedValues, where1, null);


        }
    }
}