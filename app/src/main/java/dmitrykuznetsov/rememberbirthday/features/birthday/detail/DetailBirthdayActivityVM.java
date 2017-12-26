package dmitrykuznetsov.rememberbirthday.features.birthday.detail;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.data.PersonData;
import dmitrykuznetsov.rememberbirthday.interfaces.ServiceCallback;
import dmitrykuznetsov.rememberbirthday.model.Person;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.common.base.BaseActivityVM;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.data.IUserRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.data.UserRepo;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.input.InputDialog;

/**
 * Created by dmitry on 11.03.17.
 */

public class DetailBirthdayActivityVM extends BaseActivityVM<DetailBirthdayActivity> {

    private final static String TAG = Constants.LOG_TAG + DetailBirthdayActivityVM.class.getSimpleName();
    private UserRepo userRepo = new IUserRepo();
    private int userId;

    public final ObservableField<Person> person = new ObservableField<>();
    public final ObservableField<Boolean> isNeedUserDelete = new ObservableField<Boolean>(false) {
        @Override
        public void set(Boolean value) {
            super.set(value);
            if (value) {
                userRepo.deleteUser(activity, userId, new ServiceCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        Toast.makeText(activity, R.string.success_delete_user, Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailed(String error) {
                        errorMessage.set(error);
                    }
                });
            }
        }
    };

    public DetailBirthdayActivityVM(final DetailBirthdayActivity activity, int userId) {
        super(activity);
        this.userId = userId;
        refreshUser();
    }

    private void refreshUser() {
        userRepo.getUser(activity, userId, new ServiceCallback<PersonData>() {
            @Override
            public void onSuccess(PersonData p) {
                Person person = new Person(p.getId(), p.getName(), p.getNote(), p.getBindPhone(), p.getPathImage(), p.getDateInMillis());
                DetailBirthdayActivityVM.this.person.set(person);
            }

            @Override
            public void onFailed(String error) {
                Log.d(TAG, error);
                Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
            }
        });
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
//                intent.putExtra("position", userId);
//                activity.startActivityForResult(intent, REQUEST_ADD_CODE);
            case R.id.action_user_delete:
                InputDialog.show(activity, isNeedUserDelete, R.string.confirm_delete_user_dialog_title, R.string.confirm_delete_user_dialog_message,
                        R.string.yes, R.string.cancel);

//                DialogFragment newFragment = new ConfirmDeleteUserDialogFragment();
//                newFragment.show(activity.getFragmentManager(), "actionDeleteUser");
            case R.id.action_settings:
//                Intent settings = new Intent(this, SettingsActivity.class);
//                startActivity(settings);
        }
    }


}