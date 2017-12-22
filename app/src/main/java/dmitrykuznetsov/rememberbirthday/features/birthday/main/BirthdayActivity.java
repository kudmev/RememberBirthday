package dmitrykuznetsov.rememberbirthday.features.birthday.main;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.BR;

import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity;

import dmitrykuznetsov.rememberbirthday.databinding.ActivityBirthdayBinding;


/**
 * Created by dmitry on 12.05.17.
 */

public class BirthdayActivity extends BindingActivity<ActivityBirthdayBinding, BirthdayActivityVM> {

    @Override
    public BirthdayActivityVM onCreate() {
        return new BirthdayActivityVM(this);
    }

    @Override
    public int getVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_birthday;
    }

}