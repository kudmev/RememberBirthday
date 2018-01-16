package dmitrykuznetsov.rememberbirthday.common.base;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;

import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel;

import dmitrykuznetsov.rememberbirthday.common.support.Utils;

/**
 * Created by vernau on 3/15/17.
 */

public abstract class BaseActivityVM<A extends AppCompatActivity> extends ActivityViewModel<A> {

    public final ObservableBoolean isLoading = new ObservableBoolean();
    public final ObservableField<String> errorMessage = new ObservableField<String>() {
        @Override
        public void set(String value) {
            super.set(value);
            if (value != null) {
                Utils.showToastBar(getActivity(), value);
            }
        }
    };

    public BaseActivityVM(A activity) {
        super(activity);
    }



}
