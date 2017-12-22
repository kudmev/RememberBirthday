package dmitrykuznetsov.rememberbirthday;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmitry Kuznetsov on 11.01.2016.
 */
public class ActivityAddPerson extends Activity  {
    private long millis;
    private Calendar userCalendar = Calendar.getInstance();
    private Cursor c;
    private int uid;
    private Bundle bundle = new Bundle();
    private Bitmap userBitmap = null;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int alarmType = AlarmManager.RTC_WAKEUP;
    private String ALARM_ACTION = AlarmNotificationReceiver.ACTION_SEND_NOTIFICATION;

    public static final int REQUEST_GALLERY = 1;
    public static final int PICK_CONTACT = 3;
    public static final int SINGLE_ROW = 1;
    public static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("dmitrykuznetsov.rememberbirthday.provider", "/#", SINGLE_ROW);
    }

    private EditText textName, textNote, textPhone;
    private TextView textDate, textTitlePhone;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addperson);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        textName = (EditText) findViewById(R.id.add_name_person);
        textNote = (EditText) findViewById(R.id.add_note_person);
        textDate = (TextView) findViewById(R.id.add_date_person);
        textTitlePhone = (TextView) findViewById(R.id.text_title_phone);
        textPhone = (EditText) findViewById(R.id.add_phone_person);
        button = (Button) findViewById(R.id.add_contact_person);

        imageView = (ImageView) findViewById(R.id.user_image);

        uid = getIntent().getIntExtra("position", 0);
        if (uid != 0) {
            ContentResolver cr = getContentResolver();
            String where = RememberContentProvider.UID + "=" + uid;
            c = cr.query(RememberContentProvider.CONTENT_URI, null, where, null, null);

            if (c.getCount() != 0) {
                c.moveToNext();

                String name = c.getString(c.getColumnIndex(RememberContentProvider.NAME));
                String note = c.getString(c.getColumnIndex(RememberContentProvider.NOTE));
                millis = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
                int age = c.getInt(c.getColumnIndex(RememberContentProvider.AGE_PERSON));
                String path = c.getString(c.getColumnIndex(RememberContentProvider.PATHIMAGE));
                String phone = c.getString(c.getColumnIndex(RememberContentProvider.PHONE_NUMBER));

                userCalendar.setTimeInMillis(millis);
                userCalendar.add(Calendar.YEAR, -age - 1);

                //bundle=new Bundle();
                bundle.putLong("millis", userCalendar.getTimeInMillis());

                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                String dateString = " " + formatter.format(new Date(userCalendar.getTimeInMillis()));

                textName.setText(name);
                textDate.setText(dateString);
                textNote.setText(note);
                if (phone != null) {
                    textTitlePhone.setVisibility(View.GONE);
                    textPhone.setVisibility(View.GONE);
                    textPhone.setText(phone);
                }

                userBitmap = MyHelperClass.loadImageFromStorage(path, uid);
                if (userBitmap != null)
                    imageView.setImageBitmap(userBitmap);
            }
        }

        getActionBar().setTitle(R.string.action_add_name);
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    }

    @Override
    protected void onPause() {
        Log.d("onPause", "GO");
        super.onPause();

    }

    public void showDatePickerDialog(View v) {
        Log.d("showDatePickerDialog", "GO");
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "datePicker");
    }

//    @Override
//    public void onSetDate(Calendar calendar) {
//        userCalendar = calendar;
//        millis = calendar.getTimeInMillis();
//
//        bundle=new Bundle();
//        bundle.putLong("millis", userCalendar.getTimeInMillis());
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
//        String dateString = " " + formatter.format(new Date(millis));
//
//        textDate.setText(dateString);
//        textDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.calendar_check, 0, 0, 0);
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_save:
                String name = textName.getText().toString();
                String note = textNote.getText().toString();
                String phone = textPhone.getText().toString();


                if (phone.equals(""))
                    Log.d("phoneempty", "Yes");


                if (phone == null)
                    Log.d("phoneNull", "Yes");

                if ("".equals(name)) {
                    Toast.makeText(getApplicationContext(), R.string.wrong_name, Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (userCalendar == null) {
                    Toast.makeText(getApplicationContext(), R.string.wrong_date, Toast.LENGTH_SHORT).show();
                    return true;
                }

                int year = userCalendar.get(Calendar.YEAR);

                Calendar calendar = Calendar.getInstance();
                int curYear = calendar.get(Calendar.YEAR);
//                userCalendar.set(Calendar.YEAR, curYear);
                long age = curYear - year - 1;

//                if (calendar.after(userCalendar)) {
//                    if (calendar.get(Calendar.DAY_OF_YEAR)!=userCalendar.get(Calendar.DAY_OF_YEAR)) {
//                        userCalendar.set(Calendar.YEAR, curYear+1);
//                        age++;
//                    }
//
//                }

//                if (calendar.get(Calendar.DAY_OF_YEAR) > userCalendar.get(Calendar.DAY_OF_YEAR)) {
//                    userCalendar.set(Calendar.YEAR, curYear + 1);
//                    age++;
//                }

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String valuePreferenceTimeSound = sharedPreferences.getString(SettingsFragment.timePrefA_Key, "10:00:00.000").substring(0, 5);
                Integer hour = Integer.parseInt(valuePreferenceTimeSound.substring(0, 2));
                Integer minute = Integer.parseInt(valuePreferenceTimeSound.substring(3, 5));

                userCalendar.set(calendar.HOUR_OF_DAY, hour);
                userCalendar.set(calendar.MINUTE, minute);
                userCalendar.set(calendar.MILLISECOND, 0);

                millis = userCalendar.getTimeInMillis();
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                String dateString = formatter.format(new Date(millis));

                ContentResolver cr = this.getContentResolver();
                ContentValues values = new ContentValues();
                values.put(RememberContentProvider.NAME, name);
                values.put(RememberContentProvider.DATE_BIRTHDAY, dateString);
                values.put(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS, millis);
                values.put(RememberContentProvider.AGE_PERSON, age);
                values.put(RememberContentProvider.NOTE, note);
                values.put(RememberContentProvider.PHONE_NUMBER, phone);

                //String where=RememberContentProvider.UID + "=" + uid;

                if (uid == 0) {
                    Uri uri = cr.insert(RememberContentProvider.CONTENT_URI, values);
                    switch (uriMatcher.match(uri)) {
                        case SINGLE_ROW:
                            String rowID = uri.getPathSegments().get(0);
                            uid = Integer.parseInt(rowID);
                            Log.d("row", rowID);
                        default:
                            break;
                    }

                } else {
                    //where=RememberContentProvider.UID + "=" + uid;
                    cr.update(RememberContentProvider.CONTENT_URI, values, RememberContentProvider.UID + "=" + uid, null);
                }

                values.clear();

                if (userBitmap != null) {
                    String pathImage = saveToInternalSorage(userBitmap, uid);
                    Log.d("pathImage", pathImage);
                    values.put(RememberContentProvider.PATHIMAGE, pathImage);
                    cr.update(RememberContentProvider.CONTENT_URI, values, RememberContentProvider.UID + "=" + uid, null);
                }

                Intent intentToFire = new Intent(ALARM_ACTION);
                intentToFire.putExtra("milliseconds", millis);
                intentToFire.putExtra("rowId", uid);
                pendingIntent = PendingIntent.getBroadcast(this, uid, intentToFire, pendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.set(alarmType, millis, pendingIntent);

                setResult(RESULT_OK);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void onClickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_GALLERY:
                    Uri selectedImageUri = data.getData();
                    try {
                        userBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                                selectedImageUri);
                        userBitmap = getRoundedShape(userBitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(userBitmap);
                    break;

                case PICK_CONTACT:
                    Uri contactData = data.getData();

                    Cursor cursor = getContentResolver().query(contactData, null, null,
                            null, null);
                    if (cursor.moveToNext()) {
                        int columnIndex_ID = cursor
                                .getColumnIndex(ContactsContract.Contacts._ID);
                        String contactID = cursor.getString(columnIndex_ID);

                        int columnIndex_HASPHONENUMBER = cursor
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                        String stringHasPhoneNumber = cursor
                                .getString(columnIndex_HASPHONENUMBER);

                        if (stringHasPhoneNumber.equalsIgnoreCase("1")) {
                            Cursor cursorNum = getContentResolver()
                                    .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                            null,
                                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                                    + "=" + contactID, null, null);

                            // Get the first phone number
                            if (cursorNum.moveToNext()) {
                                int columnIndex_number = cursorNum
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                                String stringNumber = cursorNum
                                        .getString(columnIndex_number);
                                textPhone.setText(stringNumber);
                                textPhone.setVisibility(View.VISIBLE);
                                textTitlePhone.setVisibility(View.VISIBLE);
                            }
                        } else {
                            textPhone.setText("");
                            textPhone.setVisibility(View.INVISIBLE);
                            textTitlePhone.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "��� ������",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 144;
        int targetHeight = 144;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(targetBitmap);

        Paint paint = new Paint();
        Rect r = new Rect(0, 0, targetWidth, targetHeight);
        final int color = 0xfff1f1f1;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawRect(r, paint);

        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;

        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);

        return targetBitmap;
    }

    private String saveToInternalSorage(Bitmap bitmapImage, int position) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, position + ".jpg");
        Log.d("file", mypath.getAbsolutePath());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return directory.getAbsolutePath();
    }

    public void pickContact(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }


}
