package dmitrykuznetsov.rememberbirthday.common.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerBindingAdapter;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;

/**
 * Created by vernau on 4/18/17.
 */

public abstract class  AbstractListVM<A extends AppCompatActivity, T> extends BaseActivityVM<A> {


    public final RecyclerConfiguration recyclerConfiguration = new RecyclerConfiguration();

    public AbstractListVM(A activity) {
        super(activity);
    }

    public RecyclerBindingAdapter<T> initAdapter(LinearLayoutManager layoutManager) {
        RecyclerBindingAdapter<T> adapter = new RecyclerBindingAdapter<>(getLayoutId(), getVariable(), getAdapterList());
        adapter.setOnItemClickListener(((position, item, holder) -> onRecyclerItemClick(item)));

        recyclerConfiguration.setLayoutManager(layoutManager);
        recyclerConfiguration.setItemAnimator(new DefaultItemAnimator());
        recyclerConfiguration.setAdapter(adapter);
        return adapter;
    }

    protected abstract void onRecyclerItemClick(T item);

    protected abstract List<T> getAdapterList();

    protected abstract int getVariable();

    protected abstract int getLayoutId();

}
