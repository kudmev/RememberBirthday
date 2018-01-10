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

public abstract class AbstractListActivityVM<A extends AppCompatActivity, T> extends BaseActivityVM<A> {

    public final RecyclerConfiguration recyclerConfiguration;
    protected final RecyclerBindingAdapter<T> adapter;

    public AbstractListActivityVM(A activity, List<T> list, RecyclerConfiguration recyclerConfiguration) {
        super(activity);
        this.recyclerConfiguration = recyclerConfiguration;
        this.adapter = new RecyclerBindingAdapter<>(getLayoutId(), getVariable(), list);
        this.adapter.setOnItemClickListener(((position, item, holder) -> onRecyclerItemClick(item)));
        this.adapter.setOnLongItemClickListener(((position, item, holder) -> onRecyclerLongItemClick(item, holder)));
        recyclerConfiguration.setAdapter(adapter);
    }

    protected abstract int getLayoutId();

    protected abstract int getVariable();

    protected abstract List<T> getAdapterList();

    protected abstract void onRecyclerItemClick(T t);

    protected abstract void onRecyclerLongItemClick(T t, RecyclerBindingAdapter.BindingHolder holder);

}
