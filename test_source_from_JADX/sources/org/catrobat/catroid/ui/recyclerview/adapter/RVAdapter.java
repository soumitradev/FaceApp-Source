package org.catrobat.catroid.ui.recyclerview.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperAdapterInterface;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;

public abstract class RVAdapter<T> extends Adapter<CheckableVH> implements TouchHelperAdapterInterface {
    public boolean allowMultiSelection = true;
    List<T> items;
    private OnItemClickListener<T> onItemClickListener;
    private SelectionListener selectionListener;
    MultiSelectionManager selectionManager = new MultiSelectionManager();
    public boolean showCheckBoxes = false;

    public interface OnItemClickListener<T> {
        void onItemClick(T t);

        void onItemLongClick(T t, CheckableVH checkableVH);
    }

    public interface SelectionListener {
        void onSelectionChanged(int i);
    }

    RVAdapter(List<T> items) {
        this.items = items;
    }

    public void setSelectionListener(SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CheckableVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CheckableVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_with_checkbox, parent, false));
    }

    public void onBindViewHolder(final CheckableVH holder, int position) {
        final T item = this.items.get(position);
        holder.checkBox.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RVAdapter.this.onCheckBoxClick(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RVAdapter.this.onItemClickListener.onItemClick(item);
            }
        });
        holder.itemView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                RVAdapter.this.onItemClickListener.onItemLongClick(item, holder);
                return true;
            }
        });
        holder.checkBox.setVisibility(this.showCheckBoxes ? 0 : 8);
        holder.checkBox.setChecked(this.selectionManager.isPositionSelected(position));
    }

    protected void onCheckBoxClick(int position) {
        if (this.allowMultiSelection) {
            this.selectionManager.toggleSelection(position);
        } else {
            boolean currentState = this.selectionManager.isPositionSelected(position);
            for (Integer i : this.selectionManager.getSelectedPositions()) {
                int i2 = i.intValue();
                this.selectionManager.setSelectionTo(false, i2);
                notifyItemChanged(i2);
            }
            this.selectionManager.setSelectionTo(currentState ^ 1, position);
            notifyItemChanged(position);
        }
        this.selectionListener.onSelectionChanged(this.selectionManager.getSelectedPositions().size());
    }

    public boolean add(T item) {
        if (!this.items.add(item)) {
            return false;
        }
        notifyItemInserted(this.items.indexOf(item));
        return true;
    }

    public boolean remove(T item) {
        if (!this.items.remove(item)) {
            return false;
        }
        notifyItemRemoved(this.items.indexOf(item));
        return true;
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(this.items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        this.selectionManager.updateSelection(fromPosition, toPosition);
        return true;
    }

    public List<T> getSelectedItems() {
        List<T> selectedItems = new ArrayList();
        for (Integer position : this.selectionManager.getSelectedPositions()) {
            selectedItems.add(this.items.get(position.intValue()));
        }
        return selectedItems;
    }

    public boolean setSelection(T item, boolean selection) {
        if (this.items.indexOf(item) == -1) {
            return false;
        }
        this.selectionManager.setSelectionTo(selection, this.items.indexOf(item));
        return true;
    }

    public void toggleSelection() {
        if (this.selectionManager.getSelectedPositions().size() == getSelectableItemCount()) {
            clearSelection();
        } else {
            selectAll();
        }
        this.selectionListener.onSelectionChanged(this.selectionManager.getSelectedPositions().size());
    }

    public void selectAll() {
        for (T item : this.items) {
            this.selectionManager.setSelectionTo(true, this.items.indexOf(item));
        }
        notifyDataSetChanged();
    }

    public void clearSelection() {
        this.selectionManager.clearSelection();
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return this.items.size();
    }

    public int getSelectableItemCount() {
        return getItemCount();
    }
}
