package dmitrykuznetsov.rememberbirthday.features.birthday.edit;

import android.app.Activity;
import android.content.Intent;

import dmitrykuznetsov.rememberbirthday.BR;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.databinding.ActivityEditPersonBinding;
import dmitrykuznetsov.rememberbirthday.common.base.BaseActivity;

/**
 * Created by dmitry on 18.03.17.
 */

public class EditPersonActivity extends BaseActivity<ActivityEditPersonBinding, EditPersonActivityVM> {

    public static void open(Activity activity, int id, int resultCode) {
        Intent intent = new Intent(activity, EditPersonActivity.class);
        intent.putExtra(Constants.PERSON_DATA, id);
        activity.startActivityForResult(intent, resultCode);
    }

    @Override
    public EditPersonActivityVM onCreate() {
        int id = getIntent().getIntExtra(Constants.PERSON_DATA, 0);
        return new EditPersonActivityVM(this, id);
    }

    @Override
    public int getVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_person;
    }

}