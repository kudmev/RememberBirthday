package dmitrykuznetsov.rememberbirthday.common.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.stfalcon.androidmvvmhelper.mvvm.fragments.BindingFragment;
import com.stfalcon.androidmvvmhelper.mvvm.fragments.FragmentViewModel;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by vernau on 4/12/17.
 */

public abstract class BaseFragment<VM extends FragmentViewModel, B extends ViewDataBinding> extends BindingFragment<VM, B> {

    protected void setTitleActionBar(int idRes) {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(idRes);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}