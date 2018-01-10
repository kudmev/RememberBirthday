package dmitrykuznetsov.rememberbirthday.common.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dmitrykuznetsov.rememberbirthday.common.support.Constants;

public class RecyclerBindingAdapter<T> extends RecyclerView.Adapter<RecyclerBindingAdapter.BindingHolder> {

    private final static String TAG = Constants.LOG_TAG + RecyclerBindingAdapter.class.getSimpleName();

    private int holderLayout;
    private int variableId;
    private List<T> items = new ArrayList<>();
    private OnItemClickListener<T> onItemClickListener;
    private OnLongItemClickListener<T> onLongItemClickListener;

    public RecyclerBindingAdapter(int holderLayout, int variableId, List<T> items) {
        this.holderLayout = holderLayout;
        this.variableId = variableId;
        this.items = items;
    }

    @Override
    public RecyclerBindingAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(holderLayout, parent, false);
        return new BindingHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerBindingAdapter.BindingHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");

        final int adapterPosition = holder.getAdapterPosition();

        final T item = items.get(position);

        holder.getBinding().getRoot().setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(adapterPosition, item, holder);
            }
        });

        holder.getBinding().getRoot().setOnLongClickListener(v -> {
            if (onLongItemClickListener != null) {
                onLongItemClickListener.onItemClick(adapterPosition, item, holder);
                return false;
            }
            return false;
        });
        holder.getBinding().setVariable(variableId, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T item, RecyclerBindingAdapter.BindingHolder holder);
    }

    public void setOnLongItemClickListener(OnLongItemClickListener<T> onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    public interface OnLongItemClickListener<T> {
        void onItemClick(int position, T item, RecyclerBindingAdapter.BindingHolder holder);
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
