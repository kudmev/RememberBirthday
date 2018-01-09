package dmitrykuznetsov.rememberbirthday.features.birthday.edit;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.DatePicker;

import org.joda.time.LocalDate;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.callback.ServiceCallback;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonObservable;
import dmitrykuznetsov.rememberbirthday.common.base.BaseActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.data.IUserRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.data.UserRepo;

/**
 * Created by dmitry on 18.03.17.
 */

public class EditPersonActivityVM extends AddPersonActivityVM implements DatePickerDialog.OnDateSetListener {

    public EditPersonActivityVM(EditPersonActivity activity, AddPersonInteractor addPersonInteractor, PersonData person) {
        super(activity, addPersonInteractor, person);
    }

    @Override
    public void onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                String errMessage = getMessageIfError();

                if (errMessage == null) {
                    addPersonInteractor.updatePersonData(person);
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                } else {
                    errorMessage.set(errMessage);
                }
                break;
        }
    }

}