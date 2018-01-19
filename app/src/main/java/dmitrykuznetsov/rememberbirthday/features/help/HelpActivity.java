package dmitrykuznetsov.rememberbirthday.features.help;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.BR;

import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity;

import javax.inject.Inject;

import dmitrykuznetsov.rememberbirthday.common.base.NextStackActivity;
import dmitrykuznetsov.rememberbirthday.databinding.ActivityHelpBinding;


/**
 * Created by dmitry on 1/19/18.
 */

public class HelpActivity extends NextStackActivity<ActivityHelpBinding, HelpActivityVM> {

    @Inject
    HelpActivityVM helpActivityVM;

    @Override
    public HelpActivityVM onCreate() {
        return helpActivityVM;
    }

    @Override
    public int getVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.title_activity_help);
    }
}