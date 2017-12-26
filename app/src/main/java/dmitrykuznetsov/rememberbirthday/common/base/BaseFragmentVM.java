package dmitrykuznetsov.rememberbirthday.common.base;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.stfalcon.androidmvvmhelper.mvvm.fragments.BindingFragment;
import com.stfalcon.androidmvvmhelper.mvvm.fragments.FragmentViewModel;

/**
 * Created by vernau on 5/2/17.
 */

public class BaseFragmentVM<F extends BindingFragment> extends FragmentViewModel<F> {

    public final ObservableField<String> errorMessage = new ObservableField<>();
    public final ObservableBoolean isLoading = new ObservableBoolean();

    public BaseFragmentVM(F fragment) {
        super(fragment);
    }
}
