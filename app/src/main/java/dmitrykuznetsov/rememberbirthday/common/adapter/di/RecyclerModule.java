package dmitrykuznetsov.rememberbirthday.common.adapter.di;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.adapter.DividerItemDecoration;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;

/**
 * Created by vernau on 5/25/17.
 */
@Module
public class RecyclerModule {

    @Named("linear")
    @Provides
    RecyclerView.LayoutManager provideLayoutManager(Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Named("grid")
    @Provides
    RecyclerView.LayoutManager provideGridLayoutManager(Context context) {
        return new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    RecyclerView.ItemAnimator provideItemAnimator() {
        return new DefaultItemAnimator();
    }

    @Provides
    RecyclerView.ItemDecoration provideItemDecoration(Context context) {
        return new DividerItemDecoration(context, R.drawable.divider);
    }

//    @Named("recycler_linear")
    @Provides
    RecyclerConfiguration provideLinearRecyclerConfiguration(@Named("linear") RecyclerView.LayoutManager manager, RecyclerView.ItemDecoration itemDecoration) {
        RecyclerConfiguration recyclerConfiguration = new RecyclerConfiguration();
        recyclerConfiguration.setLayoutManager(manager);
        recyclerConfiguration.setItemDecoration(itemDecoration);
        recyclerConfiguration.setNestedScrollingEnabled(false);
        return recyclerConfiguration;
    }

//    @Named("recycler_linear_main")
//    @Provides
//    RecyclerConfiguration provideLinearNestedRecyclerConfiguration(@Named("linear") RecyclerView.LayoutManager manager) {
//        RecyclerConfiguration recyclerConfiguration = new RecyclerConfiguration();
//        recyclerConfiguration.setLayoutManager(manager);
//        recyclerConfiguration.setNestedScrollingEnabled(true);
//        return recyclerConfiguration;
//    }
//
//    @Named("recycler_grid_main")
//    @Provides
//    RecyclerConfiguration provideGridRecyclerConfiguration(@Named("grid") RecyclerView.LayoutManager manager) {
//        RecyclerConfiguration recyclerConfiguration = new RecyclerConfiguration();
//        recyclerConfiguration.setLayoutManager(manager);
//        recyclerConfiguration.setNestedScrollingEnabled(true);
//        return recyclerConfiguration;
//    }
}
