package dmitrykuznetsov.rememberbirthday.features.birthday.add;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.soundcloud.android.crop.Crop;
import com.theartofdev.edmodo.cropper.CropImage;

import org.joda.time.LocalDate;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.base.BaseActivityVM;
import dmitrykuznetsov.rememberbirthday.common.data.model.Person;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractor;

public class AddPersonActivityVM extends BaseActivityVM<AddPersonActivity> implements DatePickerDialog.OnDateSetListener {

    private static final int REQUEST_GALLERY = 1;
    private static final int PICK_PHONE = 2;

    public final Person person = new Person();

    private AddPersonInteractor addPersonInteractor;

    public AddPersonActivityVM(AddPersonActivity activity, AddPersonInteractor addPersonInteractor) {
        super(activity);
        this.addPersonInteractor = addPersonInteractor;
    }

    public void addImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, REQUEST_GALLERY);
    }

    public void showDatePickerDialog() {
        LocalDate localDate = new LocalDate(person.getDateInMillis());
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme,
                this, localDate.getYear(), localDate.getMonthOfYear() - 1, localDate.getDayOfMonth());
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        LocalDate localDate = new LocalDate(year, month + 1, day);
        person.setDateInMillis(localDate.toDate().getTime());
    }

    public void pickPhone() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        activity.startActivityForResult(intent, PICK_PHONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_GALLERY:
                    Uri inputUri = data.getData();
                    CropImage.activity(inputUri)
                            .setMinCropResultSize(128,128)
                            .setAspectRatio(1,1)
                            .start(getActivity());
                    break;
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri resultUri = result.getUri();
                    String pathImage = resultUri.getPath();
                    person.setPathImage(pathImage);
                    break;
                case PICK_PHONE:
                    Uri contactData = data.getData();
                    String phone = addPersonInteractor.getPhone(contactData);
                    person.setBindPhone(phone);
                    break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public void onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                String errMessage = getMessageIfError();

                if (errMessage == null) {
                    addPersonInteractor.addPersonData(person);
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                } else {
                    errorMessage.set(errMessage);
                }
                break;
        }
    }

    private String getMessageIfError() {
        String message = null;

        if (person.getName().equals("")) {
            message = activity.getString(R.string.wrong_name);
        }

        if (person.getDateInMillis() == 0) {
            message = activity.getString(R.string.wrong_date_millis);
        }
        return message;
    }

}