package dmitrykuznetsov.rememberbirthday.common.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerBindingAdapter;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;

/**
 * Created by vernau on 4/18/17.
 */

public abstract class AbstractListActivityVM<A extends AppCompatActivity, T> extends NetworkActivityVM<A> {

    public final RecyclerConfiguration recyclerConfiguration;

    public AbstractListActivityVM(A activity, RecyclerConfiguration recyclerConfiguration) {
        super(activity);
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
