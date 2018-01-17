package dmitrykuznetsov.rememberbirthday.features.birthday.add;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.theartofdev.edmodo.cropper.CropImage;

import org.joda.time.LocalDate;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.base.BaseActivity;
import dmitrykuznetsov.rememberbirthday.common.base.BasePermissionActivityVM;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.permissions.PermissionsStorage;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractor;

public class AddPersonActivityVM extends BasePermissionActivityVM<BaseActivity> implements DatePickerDialog.OnDateSetListener {

    private static final int REQUEST_GALLERY = 1;
    private static final int PICK_PHONE = 2;

    public final PersonData person;

    protected AddPersonInteractor addPersonInteractor;

    private boolean isClickOnPickPhone;
    private boolean isClickOnAddImage;

    public AddPersonActivityVM(BaseActivity activity, AddPersonInteractor addPersonInteractor, PermissionsStorage permissionsStorage, PersonData person) {
        super(activity, permissionsStorage);
        this.addPersonInteractor = addPersonInteractor;
        this.person = person;
    }

    public void addImage() {
        isClickOnAddImage = true;
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, this::requestAddImage);
    }

    private void requestAddImage() {
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
        isClickOnPickPhone = true;
        requestPermission(Manifest.permission.READ_CONTACTS, this::requestPhone);
    }

    private void requestPhone() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        activity.startActivityForResult(intent, PICK_PHONE);
    }

    @Override
    protected void permissionOK(int requestCode) {
        switch (requestCode) {
            case PermissionsStorage.PERMISSION_REQUEST_READ_CONTACTS:
                if (isClickOnPickPhone) {
                    requestPhone();
                }
                break;
            case PermissionsStorage.PERMISSION_REQUEST_WRITE_STORAGE:
                if (isClickOnAddImage) {
                    requestAddImage();
                }
                break;
        }
        isClickOnPickPhone = false;
        isClickOnAddImage = false;
    }

    @Override
    protected void permissionCancel(int requestCode) {
        switch (requestCode) {
            case PermissionsStorage.PERMISSION_REQUEST_READ_CONTACTS:
                isClickOnPickPhone = false;
                break;
            case PermissionsStorage.PERMISSION_REQUEST_WRITE_STORAGE:
                isClickOnAddImage = false;
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_GALLERY:
                    Uri inputUri = data.getData();
                    CropImage.activity(inputUri)
                            .setMinCropResultSize(128, 128)
                            .setAspectRatio(1, 1)
                            .setRequestedSize(256, 256)
                            .start(getActivity());
                    break;
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    String pathImage = addPersonInteractor.getPathImage(data);
                    person.setPathImage(pathImage);
                    break;
                case PICK_PHONE:
                    if (hasPermission(Manifest.permission.READ_CONTACTS)) {
                        String phone = addPersonInteractor.getPhone(data);
                        person.setBindPhone(phone);
                    } else {
                        errorMessage.set(activity.getString(R.string.error_permission_denied_read_contacts));
                    }
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
                    addPersonInteractor.addPersonData(person)
                            .subscribe(this::onCompleteAdd, this::onErrorAdd);

                } else {
                    errorMessage.set(errMessage);
                }
                break;
        }
    }

    private void onCompleteAdd() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    private void onErrorAdd(Throwable throwable) {
        errorMessage.set(activity.getString(R.string.error_person_not_add));
    }

    protected String getMessageIfError() {
        String message = null;

        if (person.getDateInMillis() == 0) {
            message = activity.getString(R.string.wrong_date_millis);
        }

        if (person.getName() == null || person.getName().equals("")) {
            message = activity.getString(R.string.wrong_name);
        }

        return message;
    }



}