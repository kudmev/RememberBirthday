package dmitrykuznetsov.rememberbirthday.features.birthday.detail;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.base.BaseActivityVM;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.input.InputDialog;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.interactor.DetailBirthdayInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.edit.EditPersonActivity;
import io.reactivex.disposables.CompositeDisposable;

import static android.app.Activity.RESULT_OK;

public class DetailBirthdayActivityVM extends BaseActivityVM<DetailBirthdayActivity> {

    private final static String TAG = Constants.LOG_TAG + DetailBirthdayActivityVM.class.getSimpleName();

    private CompositeDisposable disposables = new CompositeDisposable();
    private DetailBirthdayInteractor interactor;
    private InputDialog inputDialog;

    public PersonData personData;

    public DetailBirthdayActivityVM(final DetailBirthdayActivity activity, PersonData personData, DetailBirthdayInteractor interactor, InputDialog inputDialog) {
        super(activity);
        this.personData = personData;
        this.interactor = interactor;
        this.inputDialog = inputDialog;
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
                Intent intent = new Intent(activity, EditPersonActivity.class);
                intent.putExtra(Constants.PERSON_DATA, personData);
                activity.startActivityForResult(intent, Constants.RESULT_ADD_PERSON);
                break;
            case R.id.action_user_delete:
                inputDialog.show();
                break;
        }
    }

    public void deletePerson() {
        disposables.add(interactor.deletePerson(personData.getId())
                .subscribe(this::onSuccessDeletePerson, this::onErrorDeletePerson));
    }

    private void onErrorDeletePerson(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }

    private void onSuccessDeletePerson() {
        Toast.makeText(activity, R.string.toast_success_delete_user, Toast.LENGTH_LONG).show();
        activity.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.RESULT_ADD_PERSON) {
                getPerson();
            }
        }
    }

    private void getPerson() {
        disposables.add(interactor.getPerson(personData.getId())
                .subscribe(this::onSuccessGetPerson, this::onErrorGetPerson));
    }

    private void onSuccessGetPerson(PersonData personData) {
        this.personData.setPersonData(personData);
    }

    private void onErrorGetPerson(Throwable throwable) {
        errorMessage.set(getActivity().getString(R.string.error_user_not_found));
    }

    public void onClickCall(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(activity.getString(R.string.tel_to) + personData.getBindPhone()));
        getActivity().startActivity(intent);
    }

    public void onClickSms(View view) {
        Uri uri = Uri.parse(activity.getString(R.string.sms_to) + personData.getBindPhone());
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //intent.putExtra("sms_body", R.string.happy_birthday);
        getActivity().startActivity(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}