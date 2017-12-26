package dmitrykuznetsov.rememberbirthday.common.adapter;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import dmitrykuznetsov.rememberbirthday.BR;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;


public class RecyclerConfiguration extends BaseObservable {

    private static final String TAG = Constants.LOG_TAG + RecyclerConfiguration.class.getSimpleName();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemAnimator itemAnimator;
    private RecyclerView.Adapter adapter;
    private RecyclerView.ItemDecoration itemDecoration;
    private boolean nestedScrollingEnabled;

    @Bindable
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        notifyPropertyChanged(BR.layoutManager);
    }

    @Bindable
    public RecyclerView.ItemAnimator getItemAnimator() {
        return itemAnimator;
    }

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        this.itemAnimator = itemAnimator;
        notifyPropertyChanged(BR.itemAnimator);
    }

    @Bindable
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public RecyclerView.ItemDecoration getItemDecoration() {
        return itemDecoration;
    }

    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public boolean getNestedScrollingEnabled() {
        return nestedScrollingEnabled;
    }

    public void setNestedScrollingEnabled(boolean nestedScrollingEnabled) {
        this.nestedScrollingEnabled = nestedScrollingEnabled;
        notifyPropertyChanged(BR.adapter);
    }

    @BindingAdapter({"configuration"})
    public static void configureRecyclerView(RecyclerView recyclerView, RecyclerConfiguration configuration) {
        Log.d(TAG, "configureRecyclerView");

        recyclerView.setLayoutManager(configuration.getLayoutManager());

        RecyclerView.ItemAnimator animator = configuration.getItemAnimator();
        if (animator != null) {
            recyclerView.setItemAnimator(animator);
        }
        recyclerView.setAdapter(configuration.getAdapter());

        RecyclerView.ItemDecoration itemDecoration = configuration.getItemDecoration();
        if (itemDecoration != null) {
            recyclerView.addItemDecoration(itemDecoration);
        }

        boolean nestedScrollingEnabled = configuration.getNestedScrollingEnabled();
        recyclerView.setNestedScrollingEnabled(nestedScrollingEnabled);
    }
}
