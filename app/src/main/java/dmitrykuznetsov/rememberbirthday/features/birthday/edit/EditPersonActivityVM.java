package dmitrykuznetsov.rememberbirthday.features.birthday.edit;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.MenuItem;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.permissions.PermissionsStorage;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.interactor.AddPersonInteractor;

/**
 * Created by dmitry on 18.03.17.
 */

public class EditPersonActivityVM extends AddPersonActivityVM implements DatePickerDialog.OnDateSetListener {

    public EditPersonActivityVM(EditPersonActivity activity, AddPersonInteractor addPersonInteractor, PermissionsStorage permissionsStorage, PersonData person) {
        super(activity, addPersonInteractor, permissionsStorage, person);
    }

    @Override
    public void onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                String errMessage = getMessageIfError();

                if (errMessage == null) {
                    addPersonInteractor.updatePersonData(person)
                            .subscribe(this::onCompleteEdit, this::onErrorEdit);
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                } else {
                    errorMessage.set(errMessage);
                }
                break;
        }
    }

    private void onCompleteEdit() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    private void onErrorEdit(Throwable throwable) {
        errorMessage.set(activity.getString(R.string.error_person_not_edit));
    }

}