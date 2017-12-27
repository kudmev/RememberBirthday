package dmitrykuznetsov.rememberbirthday.features.birthday.main;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.BR;

import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity;

import javax.inject.Inject;

import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;
import dmitrykuznetsov.rememberbirthday.databinding.ActivityBirthdayBinding;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor.BirthdaysInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor.BirthdaysInteractorImpl;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.repo.UsersRepoImpl;


/**
 * Created by dmitry on 12.05.17.
 */

public class BirthdaysActivity extends BindingActivity<ActivityBirthdayBinding, BirthdaysActivityVM> {

    @Inject
    BirthdaysActivityVM birthdaysActivityVM;

//    @Inject
//    RecyclerView.ItemDecoration dividerItemDecoration;

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