package dmitrykuznetsov.rememberbirthday.features.birthday.add;

import android.app.Activity;
import android.content.Intent;

import dmitrykuznetsov.rememberbirthday.BR;
import dmitrykuznetsov.rememberbirthday.R;

import dmitrykuznetsov.rememberbirthday.databinding.ActivityAddPersonBinding;
import dmitrykuznetsov.rememberbirthday.common.base.BaseActivity;

/**
 * Created by dmitry on 18.03.17.
 */

public class AddPersonActivity extends BaseActivity<ActivityAddPersonBinding, AddPersonActivityVM> {

    public static void open(Activity activity, int resultCode) {
        Intent intent = new Intent(activity, AddPersonActivity.class);
        activity.startActivityForResult(intent, resultCode);
    }

    @Override
    public AddPersonActivityVM onCreate() {
        return new AddPersonActivityVM(this);
    }

    @Override
    public int getVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_person;
    }

}