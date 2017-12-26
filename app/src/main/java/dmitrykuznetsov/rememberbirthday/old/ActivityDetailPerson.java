package dmitrykuznetsov.rememberbirthday.old;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.features.birthday.edit.EditPersonActivity;

/**
 * Created by Dmitry Kuznetsov on 11.01.2016.
 */
public class ActivityDetailPerson extends Activity implements ConfirmDeleteUserDialogFragment.NoticeDialogListener {
    public static final int REQUEST_ADD_CODE = 0;

    private TextView textName, textAge, textDate, textNote, textPhone, textTitlePhone, textTitleNote;
    private ImageView imageView;

    private String name, note, date, pathImage, phone;
    private int age;
    private long milliseconds;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailperson);

        position = getIntent().getIntExtra("position", 0);

        textName = (TextView) findViewById(R.id.detail_name_person);
        textAge = (TextView) findViewById(R.id.detail_age_person);
        textDate = (TextView) findViewById(R.id.detail_date_person);
        textNote = (TextView) findViewById(R.id.detail_note_person);
        textPhone = (TextView) findViewById(R.id.detail_phone_person);
        textTitlePhone = (TextView) findViewById(R.id.detail_title_phone);
        textTitleNote = (TextView) findViewById(R.id.detail_title_note);
        imageView = (ImageView) findViewById(R.id.user_image);


        loadDataUser();

        getActionBar().setTitle(name);
        getActionBar().setHomeButtonEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void loadDataUser() {
        ContentResolver cr = getContentResolver();
        String where = RememberContentProvider.UID + "=" + position;
        Cursor c = cr.query(RememberContentProvider.CONTENT_URI, null, where, null, null);

        if (c.getCount() != 0) {
            while (c.moveToNext()) {
                name = c.getString(c.getColumnIndex(RememberContentProvider.NAME));
                note = c.getString(c.getColumnIndex(RememberContentProvider.NOTE));
                date = c.getString(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY));
                age = c.getInt(c.getColumnIndex(RememberContentProvider.AGE_PERSON));
                pathImage = c.getString(c.getColumnIndex(RememberContentProvider.PATHIMAGE));
                milliseconds = c.getLong(c.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
                phone = c.getString(c.getColumnIndex(RememberContentProvider.PHONE_NUMBER));

                ViewGroup viewGroup = (ViewGroup) findViewById(R.id.detail_relative);
                if ("".equals(phone)) {
                    textTitlePhone.setVisibility(View.GONE);
                    viewGroup.setVisibility(View.GONE);
                } else {
                    viewGroup.setVisibility(View.VISIBLE);
                    textPhone.setText(phone);
                    textTitlePhone.setVisibility(View.VISIBLE);
                }

            }
        }

        if (MyHelperClass.isUserBirthdayToday(milliseconds) == true) {
            date = getApplicationContext().getString(R.string.today);
            age++;
        }

        Bitmap userImage = MyHelperClass.loadImageFromStorage(pathImage, position);
        if (userImage != null)
            imageView.setImageBitmap(userImage);

        textName.setText(name);
        textAge.setText(MyHelperClass.getAgeSuffix(age, getApplicationContext(), 1));
        textDate.setText(date);
        if (note.equals("")) {
            textNote.setVisibility(View.GONE);
            textTitleNote.setVisibility(View.GONE);
        } else {
            textNote.setVisibility(View.VISIBLE);
            textTitleNote.setVisibility(View.VISIBLE);
            textNote.setText(note);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_user_edit:
                intent = new Intent(this, EditPersonActivity.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_ADD_CODE);
                return true;
            case R.id.action_user_delete:
                DialogFragment newFragment = new ConfirmDeleteUserDialogFragment();
                newFragment.show(getFragmentManager(), "actionDeleteUser");
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
            if (requestCode == 0) {
                loadDataUser();
            }
        }
    }

    @Override
    public void onDialogPositiveClick() {
        ContentResolver cr = getContentResolver();
        String where = RememberContentProvider.UID + "=" + position;
        cr.delete(RememberContentProvider.CONTENT_URI, where, null);
        Toast.makeText(this, R.string.success_delete_user, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onDialogNegativeClick() {

    }

    public void onClickCall(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent);

    }

    public void onClickSms(View view) {
        Uri uri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //intent.putExtra("sms_body", R.string.happy_birthday);
        startActivity(intent);

    }

}
