package dmitrykuznetsov.rememberbirthday;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;



//import android.support.v4.app.FragmentActivity;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>, SharedPreferences.OnSharedPreferenceChangeListener {

    TextView textAge, textName, textDate, textTitleNear, textAddFirstPerson;
    ListView listView;
    SimpleCursorAdapter scAdapter;

    public static final String APP_SETTINGS = "settings";
    public static final String SETTINGS_DATE_REFRESH = "";
    public static final String SETTINGS_DATE_NOTIFICATION = "";
    public static final String FIRST_START = "";

    public static final int RESULT_CODE = 1;

    private static final int URL_LOADER = 0;
    private static final String[] from = new String[]{RememberContentProvider.NAME, RememberContentProvider.DATE_BIRTHDAY, RememberContentProvider.AGE_PERSON, RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS, RememberContentProvider.PATHIMAGE};
    private static final int[] to = new int[]{R.id.text_name, R.id.text_date, R.id.text_age};

    //private Cursor c;
    private AlarmManager alarmManager;//= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    private PendingIntent pendingIntent;
    private int alarmType = AlarmManager.RTC_WAKEUP;
    private String ALARM_ACTION = AlarmNotificationReceiver.ACTION_SEND_NOTIFICATION;

    private static final int SINGLE_ROW = 1;
    private static final UriMatcher uriMatcher;

    Set<PendingIntent> set = new HashSet<>();

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("dmitrykuznetsov.rememberbirthday.provider", "/#", SINGLE_ROW);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        ViewGroup parent = (ViewGroup) findViewById(R.id.my_relative_layout);
        View rowView = inflater.inflate(R.layout.list_contact, parent, false);

        listView = (ListView) findViewById(R.id.list_item);
        textTitleNear = (TextView) findViewById(R.id.near_daybirthdays);
        textAddFirstPerson = (TextView) findViewById(R.id.add_first_person);

        textAge = (TextView) rowView.findViewById(R.id.text_age);
        textName = (TextView) rowView.findViewById(R.id.text_name);
        textDate = (TextView) rowView.findViewById(R.id.text_date);


        scAdapter = new MySimpleCursorAdapter(this, R.layout.list_contact, null, from, to, 0);
        listView.setAdapter(scAdapter);
        this.getLoaderManager().initLoader(URL_LOADER, null, this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mySettings = getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);

//        if (mySettings.contains(FIRST_START)) {
//            textTitleNear.setVisibility(View.VISIBLE);
//            textAddFirstPerson.setVisibility(View.GONE);
//
//        } else {
//            textAddFirstPerson.setVisibility(View.VISIBLE);
//            textTitleNear.setVisibility(View.GONE);
//            SharedPreferences.Editor editor = mySettings.edit();
//            editor.putInt(MainActivity.FIRST_START, 1);
//            editor.apply();
//        }

        if (mySettings.contains(SETTINGS_DATE_NOTIFICATION)) {
            int dayOfYearSetting = mySettings.getInt(SETTINGS_DATE_NOTIFICATION, 0);
            Calendar calendar = Calendar.getInstance();
            int curDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

            Log.d("dayOfYearNotification", String.valueOf(dayOfYearSetting));
            Log.d("curDayOfYear", String.valueOf(curDayOfYear));
            if (dayOfYearSetting != curDayOfYear) {
                this.startService(new Intent(MainActivity.this, ServiceSendNotification.class));
            }

        }

        getLoaderManager().getLoader(0).forceLoad();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService((Context.SEARCH_SERVICE));
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        int imageId = getResources().getIdentifier("android:id/search_button", null, null);
        //ViewGroup v = (ViewGroup) searchView.findViewById(linlayId);
        ImageView imageView = (ImageView) searchView.findViewById(imageId);
        imageView.setBackgroundResource(R.drawable.search);


        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        searchPlate.setBackgroundResource(R.drawable.textfield_searchview_holo_light);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_name:
                addFirstPerson(item.getActionView());
                return true;
            case R.id.action_search:
                //item.setShowAsAction(item.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                return true;

            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                //Toast.makeText(getApplicationContext(), "onActivityResult", Toast.LENGTH_LONG).show();
                getLoaderManager().getLoader(0).forceLoad();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        String where = null;
        if (bndl != null) {
            where = bndl.getString("where");
        }
        return new CursorLoader(this, RememberContentProvider.CONTENT_URI, null, where, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        scAdapter.swapCursor(null);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String[] projection = new String[]{RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS, RememberContentProvider.UID};
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        Bundle bundle = new Bundle();
        switch (key) {

            case SettingsFragment.timePrefA_Key:
                String valuePreferenceTimeSound = sharedPref.getString(SettingsFragment.timePrefA_Key, "10:00:00.000").substring(0, 5);
                Integer hour = Integer.parseInt(valuePreferenceTimeSound.substring(0, 2));
                Integer minute = Integer.parseInt(valuePreferenceTimeSound.substring(3, 5));

                ContentResolver cr = getContentResolver();
                Cursor c = cr.query(RememberContentProvider.CONTENT_URI, projection, null, null, null);
                while (c.moveToNext()) {
                    long millis = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
                    int rowId = c.getInt(c.getColumnIndex(RememberContentProvider.UID));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(millis);

//                    calendar.set(Calendar.HOUR_OF_DAY, hour);
//                    calendar.set(Calendar.MINUTE, minute);

                    long setTime = calendar.getTimeInMillis();

                    ContentValues updatedValues = new ContentValues();
                    updatedValues.put(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS, setTime);
                    String where1 = RememberContentProvider.UID + "=" + String.valueOf(rowId);
                    cr.update(RememberContentProvider.CONTENT_URI, updatedValues, where1, null);

                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intentToFire = new Intent(ALARM_ACTION);
                    intentToFire.putExtra("milliseconds", setTime);
                    intentToFire.putExtra("rowId", rowId);

                    pendingIntent = PendingIntent.getBroadcast(this, rowId, intentToFire, pendingIntent.FLAG_CANCEL_CURRENT);
                    Log.d("pendingintent", pendingIntent.toString());
                    alarmManager.set(alarmType, setTime, pendingIntent);
                }

        }


        getLoaderManager().restartLoader(0, bundle, this);
    }


    public void addFirstPerson(View view) {
//        AddPersonActivity.open(this, 0, RESULT_CODE);

    }
}


