package dmitrykuznetsov.rememberbirthday.common.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel;
import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity;

import dagger.android.AndroidInjection;
import dmitrykuznetsov.rememberbirthday.R;

/**
 * Created by vernau on 3/15/17.
 */

public abstract class BaseActivity<B extends ViewDataBinding, VM extends ActivityViewModel> extends BindingActivity<B, VM> {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
