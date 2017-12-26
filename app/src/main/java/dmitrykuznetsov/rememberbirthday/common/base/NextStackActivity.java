package dmitrykuznetsov.rememberbirthday.common.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel;

public abstract class NextStackActivity<B extends ViewDataBinding, VM extends ActivityViewModel> extends BaseActivity<B, VM> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getActivityName());
        }
    }

    protected abstract String getActivityName();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
