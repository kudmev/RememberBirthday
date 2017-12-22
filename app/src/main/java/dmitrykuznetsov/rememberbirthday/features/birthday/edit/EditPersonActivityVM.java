package dmitrykuznetsov.rememberbirthday.features.birthday.edit;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.ObservableField;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.DatePicker;

import com.soundcloud.android.crop.Crop;

import org.joda.time.LocalDate;

import java.io.File;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.data.PersonData;
import dmitrykuznetsov.rememberbirthday.common.interfaces.ServiceCallback;
import dmitrykuznetsov.rememberbirthday.common.model.Person;
import dmitrykuznetsov.rememberbirthday.common.base.BaseActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.data.IUserRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.data.UserRepo;

/**
 * Created by dmitry on 18.03.17.
 */

public class EditPersonActivityVM extends BaseActivityVM<EditPersonActivity> implements DatePickerDialog.OnDateSetListener {

    private static final int REQUEST_GALLERY = 1;
    private static final int PICK_CONTACT = 2;
    private UserRepo userRepo = new IUserRepo();

    public final ObservableField<Person> userObservable = new ObservableField<>();

    public EditPersonActivityVM(EditPersonActivity activity, int id) {
        super(activity);

        if (id != 0) {
            userRepo.getUser(getActivity(), id, new ServiceCallback<PersonData>() {
                @Override
                public void onSuccess(PersonData personData) {

                }

                @Override
                public void onFailed(String error) {

                }
            });
        }
    }

    public void addImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, REQUEST_GALLERY);
    }

    public void showDatePickerDialog() {
        LocalDate localDate = new LocalDate(userObservable.get().dateInMillis.get());

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme,
                this, localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfYear());
        dialog.show();
    }


    public void onDateSet(DatePicker view, int year, int month, int day) {
        LocalDate localDate = new LocalDate(year, month + 1, day);
        userObservable.get().dateInMillis.set(localDate.toDate().getTime());
    }

    public void pickContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        activity.startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_GALLERY:
                    Uri inputUri = data.getData();

                    Uri destination = Uri.fromFile(new File(activity.getCacheDir(), "cropped"));
                    Crop.of(inputUri, destination).asSquare().start(activity);
                    break;
                case Crop.REQUEST_CROP:
                    Uri cropUri = Crop.getOutput(data);
                    String path = cropUri.getPath();
                    userObservable.get().pathImage.set(path);
//                    try {
//                        Bitmap userBitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(),
//                                cropUri);
//                        userBitmap = getRoundedShape(userBitmap);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    imageView.setImageBitmap(userBitmap);
//                    break;
//
                case PICK_CONTACT:
//                    Uri contactData = data.getData();
//
//                    Cursor cursor = getContentResolver().query(contactData, null, null,
//                            null, null);
//                    if (cursor.moveToNext()) {
//                        int columnIndex_ID = cursor
//                                .getColumnIndex(ContactsContract.Contacts._ID);
//                        String contactID = cursor.getString(columnIndex_ID);
//
//                        int columnIndex_HASPHONENUMBER = cursor
//                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
//                        String stringHasPhoneNumber = cursor
//                                .getString(columnIndex_HASPHONENUMBER);
//
//                        if (stringHasPhoneNumber.equalsIgnoreCase("1")) {
//                            Cursor cursorNum = getContentResolver()
//                                    .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                                            null,
//                                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//                                                    + "=" + contactID, null, null);
//

//                            if (cursorNum.moveToNext()) {
//                                int columnIndex_number = cursorNum
//                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                                String stringNumber = cursorNum
//                                        .getString(columnIndex_number);
//                                textPhone.setText(stringNumber);
//                                textPhone.setVisibility(View.VISIBLE);
//                                textTitlePhone.setVisibility(View.VISIBLE);
//                            }
//                        } else {
//                            textPhone.setText("");
//                            textPhone.setVisibility(View.INVISIBLE);
//                            textTitlePhone.setVisibility(View.INVISIBLE);
//                        }
//                    } else {
//                        Toast.makeText(getApplicationContext(), "��� ������",
//                                Toast.LENGTH_LONG).show();
//                    }
//                    break;
            }
        }
    }

}