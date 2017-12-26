package dmitrykuznetsov.rememberbirthday.features.birthday.add;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.soundcloud.android.crop.Crop;

import org.joda.time.LocalDate;

import java.io.File;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.base.BaseActivityVM;
import dmitrykuznetsov.rememberbirthday.model.Person;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractorImpl;

/**
 * Created by dmitry on 18.03.17.
 */

public class AddPersonActivityVM extends BaseActivityVM<AddPersonActivity> implements DatePickerDialog.OnDateSetListener {

    private static final int REQUEST_GALLERY = 1;
    private static final int PICK_CONTACT = 2;

    public final ObservableField<Person> personObservable = new ObservableField<>(
            new Person(0, "", "", "", "", 0)
    );

//    public final ObservableField<String> name = new ObservableField<>();
//    public final ObservableField<String> note = new ObservableField<>();
//    public final ObservableField<String> bindPhone = new ObservableField<>();
//    public final ObservableField<String> pathImage = new ObservableField<>();
//    public final ObservableField<Long> dateInMillis = new ObservableField<>();

    public AddPersonInteractor addPersonInteractor;

    public AddPersonActivityVM(AddPersonActivity activity) {
        super(activity);
    }

    private Person getPerson() {
        return personObservable.get();
    }

    public void addImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, REQUEST_GALLERY);
    }

    public void showDatePickerDialog() {
        LocalDate localDate = new LocalDate(getPerson().dateInMillis.get());
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme,
                this, localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfYear());

        dialog.setTitle(getActivity().getString(R.string.dialog_select_date));
        dialog.setMessage(getActivity().getString(R.string.dialog_hint_date_message));
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        LocalDate localDate = new LocalDate(year, month + 1, day);
        getPerson().dateInMillis.set(localDate.toDate().getTime());
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
                    String id = inputUri.getLastPathSegment();
                    Uri destination = Uri.fromFile(new File(activity.getCacheDir(), "person_" + id));
                    Crop.of(inputUri, destination).asSquare().start(activity);
                    break;
                case Crop.REQUEST_CROP:
                    Uri cropUri = Crop.getOutput(data);
                    String path = cropUri.getPath();
                    getPerson().pathImage.set(path);
                    break;
                case PICK_CONTACT:
                    Uri contactData = data.getData();
                    String phone = addPersonInteractor.getPhone(contactData);
                    getPerson().bindPhone.set(phone);
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
                String errMessage = getPerson().getMessageIfError(getActivity());

                if (errMessage == null) {
                    addPersonInteractor.addPersonData(personObservable.get());
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                } else {
                    errorMessage.set(errMessage);
                }
                break;
        }
    }

}