package dmitrykuznetsov.rememberbirthday.common.base;

import com.stfalcon.androidmvvmhelper.mvvm.fragments.BindingFragment;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerBindingAdapter;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;

/**
 * Created by vernau on 4/18/17.
 */

public abstract class AbstractListFragmentVM<F extends BindingFragment, T> extends NetworkFragmentVM<F> {

    public final RecyclerConfiguration recyclerConfiguration;

    public AbstractListFragmentVM(F fragment, RecyclerConfiguration recyclerConfiguration) {
        super(fragment);
        this.recyclerConfiguration = recyclerConfiguration;
    }

    protected RecyclerBindingAdapter<T> initAdapter() {
        RecyclerBindingAdapter<T> adapter = new RecyclerBindingAdapter<>(getLayoutId(), getVariable(), getAdapterList());
        adapter.setOnItemClickListener(((position, item, holder) -> onRecyclerItemClick(item)));
        recyclerConfiguration.setAdapter(adapter);
        return adapter;
    }

    protected abstract void onRecyclerItemClick(T t);

    protected abstract List<T> getAdapterList();

    protected abstract int getVariable();

    protected abstract int getLayoutId();

}
