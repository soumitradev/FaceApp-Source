package org.catrobat.catroid.ui.recyclerview.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.ViewHolder;

public class CategoryListRVAdapter extends Adapter<ViewHolder> {
    public static final int COLLISION = 1;
    public static final int DEFAULT = 0;
    public static final int EV3 = 3;
    public static final int NXT = 2;
    private List<CategoryListItem> items;
    private OnItemClickListener onItemClickListener;

    public static class CategoryListItem {
        @Nullable
        public String header;
        public int nameResId;
        public String text;
        public int type;

        public CategoryListItem(int nameResId, String text, int type) {
            this.nameResId = nameResId;
            this.text = text;
            this.type = type;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CategoryListItemType {
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryListItem categoryListItem);
    }

    public CategoryListRVAdapter(List<CategoryListItem> items) {
        this.items = items;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CategoryListItem item = (CategoryListItem) this.items.get(position);
        if (holder.getItemViewType() == R.layout.vh_category_list_item_with_headline) {
            ((TextView) holder.itemView.findViewById(R.id.headline)).setText(((CategoryListItem) this.items.get(position)).header);
        }
        holder.title.setText(item.text);
        holder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CategoryListRVAdapter.this.onItemClickListener.onItemClick(item);
            }
        });
    }

    @LayoutRes
    public int getItemViewType(int position) {
        return ((CategoryListItem) this.items.get(position)).header != null ? R.layout.vh_category_list_item_with_headline : R.layout.vh_category_list_item;
    }

    public int getItemCount() {
        return this.items.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
