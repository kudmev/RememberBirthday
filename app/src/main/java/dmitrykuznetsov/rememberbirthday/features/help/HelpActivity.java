package dmitrykuznetsov.rememberbirthday.features.help;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.BR;

import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity;

import dmitrykuznetsov.rememberbirthday.databinding.ActivityHelpBinding;


/**
 * Created by dmitry on 1/19/18.
 */

public class HelpActivity extends BindingActivity<ActivityHelpBinding, HelpActivityVM> {

    @Override
    public HelpActivityVM onCreate() {
        return new HelpActivityVM(this);
    }

    @Override
    public int getVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

}