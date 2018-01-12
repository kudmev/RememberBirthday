package dmitrykuznetsov.rememberbirthday.features.birthday.main;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.BR;

import javax.inject.Inject;

import dmitrykuznetsov.rememberbirthday.common.base.BaseActivity;
import dmitrykuznetsov.rememberbirthday.common.support.Config;
import dmitrykuznetsov.rememberbirthday.databinding.ActivityBirthdayBinding;


/**
 * Created by dmitry on 12.05.17.
 */

public class BirthdaysActivity extends BaseActivity<ActivityBirthdayBinding, BirthdaysActivityVM> {

    @Inject
    BirthdaysActivityVM birthdaysActivityVM;

    @Override
    public BirthdaysActivityVM onCreate() {
        return birthdaysActivityVM;
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