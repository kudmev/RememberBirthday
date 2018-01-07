package dmitrykuznetsov.rememberbirthday.features.birthday.detail;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.base.BaseActivityVM;
import dmitrykuznetsov.rememberbirthday.common.data.model.Person;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.input.InputDialog;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.interactor.DetailBirthdayInteractor;

public class DetailBirthdayActivityVM extends BaseActivityVM<DetailBirthdayActivity> {

    private final static String TAG = Constants.LOG_TAG + DetailBirthdayActivityVM.class.getSimpleName();

    private DetailBirthdayInteractor interactor;
    private InputDialog inputDialog;
    private PersonData personData;

    public final ObservableField<Person> person = new ObservableField<>();

    public DetailBirthdayActivityVM(final DetailBirthdayActivity activity, PersonData personData, DetailBirthdayInteractor interactor, InputDialog inputDialog) {
        super(activity);
        this.personData = personData;
        this.interactor = interactor;
        this.inputDialog = inputDialog;
        getUser();
    }

    private void getUser() {
        interactor.getPerson(personData.getId())
                .subscribe(this::onSuccessGetPerson, this::onErrorGetPerson);
    }

    private void onSuccessGetPerson(PersonData p) {
        Person person = new Person(p);
        DetailBirthdayActivityVM.this.person.set(person);
    }

    private void onErrorGetPerson(Throwable throwable) {
        String error = throwable.getMessage();
        Log.d(TAG, error);
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public void onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_user_edit:
//                Intent intent = new Intent(this, ActivityAddPerson.class);
//                intent.putExtra("position", personId);
//                activity.startActivityForResult(intent, REQUEST_ADD_CODE);
                break;
            case R.id.action_user_delete:
                inputDialog.show();
                break;
            case R.id.action_settings:
//                Intent settings = new Intent(this, SettingsActivity.class);
//                startActivity(settings);
                break;
        }
    }


    public void deletePerson() {
        Log.d(TAG, "on click positive");
        interactor.deletePerson(personData.getId())
                .subscribe(this::onSuccessDeletePerson, this::onErrorDeletePerson);
    }

    private void onErrorDeletePerson(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }

    private void onSuccessDeletePerson(Boolean result) {
        if (result) {
            Toast.makeText(activity, R.string.toast_success_delete_user, Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }


}