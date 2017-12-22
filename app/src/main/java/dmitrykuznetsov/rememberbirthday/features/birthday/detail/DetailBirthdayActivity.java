package dmitrykuznetsov.rememberbirthday.features.birthday.detail;

import android.content.Context;
import android.content.Intent;

import dmitrykuznetsov.rememberbirthday.BR;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.databinding.ActivityDetailBirthdayBinding;
import dmitrykuznetsov.rememberbirthday.common.base.BaseActivity;


/**
 * Created by dmitry on 11.03.17.
 */

public class DetailBirthdayActivity extends BaseActivity<ActivityDetailBirthdayBinding, DetailBirthdayActivityVM> {

    public static void open(Context context, int userId) {
        Intent intent = new Intent(context, DetailBirthdayActivity.class);
        intent.putExtra(Constants.USER_ID, userId);
        context.startActivity(intent);
    }

    @Override
    public DetailBirthdayActivityVM onCreate() {
        int id = getIntent().getIntExtra(Constants.USER_ID, 0);
        return new DetailBirthdayActivityVM(this, id);
    }

    @Override
    public int getVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_birthday;
    }

}