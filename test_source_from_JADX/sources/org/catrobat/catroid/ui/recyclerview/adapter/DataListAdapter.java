package org.catrobat.catroid.ui.recyclerview.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;

public class DataListAdapter extends Adapter<CheckableVH> implements SelectionListener {
    private static final int LIST_GLOBAL = 2;
    private static final int LIST_LOCAL = 3;
    private static final int VAR_GLOBAL = 0;
    private static final int VAR_LOCAL = 1;
    public boolean allowMultiSelection = true;
    private ListRVAdapter globalListAdapter;
    private VariableRVAdapter globalVarAdapter;
    private ListRVAdapter localListAdapter;
    private VariableRVAdapter localVarAdapter;
    private SelectionListener selectionListener;

    public DataListAdapter(List<UserVariable> globalVars, List<UserVariable> localVars, List<UserList> globalLists, List<UserList> localLists) {
        this.globalVarAdapter = new VariableRVAdapter(globalVars) {
            public void onBindViewHolder(CheckableVH holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    ((TextView) holder.itemView.findViewById(R.id.headline)).setText(R.string.global_vars_headline);
                }
            }
        };
        this.globalVarAdapter.setSelectionListener(this);
        this.localVarAdapter = new VariableRVAdapter(localVars) {
            public void onBindViewHolder(CheckableVH holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    ((TextView) holder.itemView.findViewById(R.id.headline)).setText(R.string.local_vars_headline);
                }
            }

            protected void onCheckBoxClick(int position) {
                super.onCheckBoxClick(DataListAdapter.this.getRelativeItemPosition(position, 1));
            }
        };
        this.localVarAdapter.setSelectionListener(this);
        this.globalListAdapter = new ListRVAdapter(globalLists) {
            public void onBindViewHolder(CheckableVH holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    ((TextView) holder.itemView.findViewById(R.id.headline)).setText(R.string.global_lists_headline);
                }
            }

            protected void onCheckBoxClick(int position) {
                super.onCheckBoxClick(DataListAdapter.this.getRelativeItemPosition(position, 2));
            }
        };
        this.globalListAdapter.setSelectionListener(this);
        this.localListAdapter = new ListRVAdapter(localLists) {
            public void onBindViewHolder(CheckableVH holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    ((TextView) holder.itemView.findViewById(R.id.headline)).setText(R.string.local_lists_headline);
                }
            }

            protected void onCheckBoxClick(int position) {
                super.onCheckBoxClick(DataListAdapter.this.getRelativeItemPosition(position, 3));
            }
        };
        this.localListAdapter.setSelectionListener(this);
    }

    private int getRelativeItemPosition(int position, int dataType) {
        switch (dataType) {
            case 0:
                return position;
            case 1:
                return position - this.globalVarAdapter.getItemCount();
            case 2:
                return position - (this.globalVarAdapter.getItemCount() + this.localVarAdapter.getItemCount());
            case 3:
                return position - ((this.globalVarAdapter.getItemCount() + this.localVarAdapter.getItemCount()) + this.globalListAdapter.getItemCount());
            default:
                throw new IllegalArgumentException("DataType is not specified: this would throw an index out of bounds exception.");
        }
    }

    private int getDataType(int position) {
        if (position < this.globalVarAdapter.getItemCount()) {
            return 0;
        }
        if (position < this.globalVarAdapter.getItemCount() + this.localVarAdapter.getItemCount()) {
            return 1;
        }
        if (position < (this.globalVarAdapter.getItemCount() + this.localVarAdapter.getItemCount()) + this.globalListAdapter.getItemCount()) {
            return 2;
        }
        if (position < ((this.globalVarAdapter.getItemCount() + this.localVarAdapter.getItemCount()) + this.globalListAdapter.getItemCount()) + this.localListAdapter.getItemCount()) {
            return 3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("None of the sub adapters provides this position. size:");
        stringBuilder.append(getItemCount());
        stringBuilder.append("index: ");
        stringBuilder.append(position);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public void showCheckBoxes(boolean visible) {
        this.globalVarAdapter.showCheckBoxes = visible;
        this.localVarAdapter.showCheckBoxes = visible;
        this.globalListAdapter.showCheckBoxes = visible;
        this.localListAdapter.showCheckBoxes = visible;
    }

    public void setSelectionListener(SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.globalVarAdapter.setOnItemClickListener(onItemClickListener);
        this.localVarAdapter.setOnItemClickListener(onItemClickListener);
        this.globalListAdapter.setOnItemClickListener(onItemClickListener);
        this.localListAdapter.setOnItemClickListener(onItemClickListener);
    }

    @NonNull
    public CheckableVH onCreateViewHolder(@NonNull ViewGroup parent, @LayoutRes int viewType) {
        switch (viewType) {
            case R.layout.vh_list:
            case R.layout.vh_list_with_headline:
                return this.globalListAdapter.onCreateViewHolder(parent, viewType);
            case R.layout.vh_variable:
            case R.layout.vh_variable_with_headline:
                return this.globalVarAdapter.onCreateViewHolder(parent, viewType);
            default:
                throw new IllegalArgumentException("ViewType was not defined correctly.");
        }
    }

    @LayoutRes
    public int getItemViewType(int position) {
        int dataType = getDataType(position);
        position = getRelativeItemPosition(position, dataType);
        switch (dataType) {
            case 0:
            case 1:
                return position == 0 ? R.layout.vh_variable_with_headline : R.layout.vh_variable;
            case 2:
            case 3:
                return position == 0 ? R.layout.vh_list_with_headline : R.layout.vh_list;
            default:
                throw new ArrayIndexOutOfBoundsException("position is not within any of the adapters");
        }
    }

    public void onBindViewHolder(@NonNull CheckableVH holder, int position) {
        int dataType = getDataType(position);
        position = getRelativeItemPosition(position, dataType);
        switch (holder.getItemViewType()) {
            case R.layout.vh_list:
            case R.layout.vh_list_with_headline:
                if (dataType == 2) {
                    this.globalListAdapter.onBindViewHolder(holder, position);
                    return;
                } else {
                    this.localListAdapter.onBindViewHolder(holder, position);
                    return;
                }
            case R.layout.vh_variable:
            case R.layout.vh_variable_with_headline:
                if (dataType == 0) {
                    this.globalVarAdapter.onBindViewHolder(holder, position);
                    return;
                } else {
                    this.localVarAdapter.onBindViewHolder(holder, position);
                    return;
                }
            default:
                return;
        }
    }

    public void onSelectionChanged(int selectedItemCnt) {
        this.selectionListener.onSelectionChanged(((this.globalVarAdapter.getSelectedItems().size() + this.localVarAdapter.getSelectedItems().size()) + this.globalListAdapter.getSelectedItems().size()) + this.localListAdapter.getSelectedItems().size());
    }

    public void updateDataSet() {
        this.globalVarAdapter.notifyDataSetChanged();
        this.localVarAdapter.notifyDataSetChanged();
        this.globalListAdapter.notifyDataSetChanged();
        this.localListAdapter.notifyDataSetChanged();
        notifyDataSetChanged();
    }

    public void clearSelection() {
        this.globalVarAdapter.clearSelection();
        this.localVarAdapter.clearSelection();
        this.globalListAdapter.clearSelection();
        this.localListAdapter.clearSelection();
        notifyDataSetChanged();
    }

    public void remove(UserData item) {
        if (item instanceof UserVariable) {
            if (!this.globalVarAdapter.remove((UserVariable) item)) {
                this.localVarAdapter.remove((UserVariable) item);
            }
        } else if (!this.globalListAdapter.remove((UserList) item)) {
            this.localListAdapter.remove((UserList) item);
        }
        notifyDataSetChanged();
    }

    public List<UserData> getItems() {
        List<UserData> items = new ArrayList();
        items.addAll(this.globalVarAdapter.getItems());
        items.addAll(this.localVarAdapter.getItems());
        items.addAll(this.globalListAdapter.getItems());
        items.addAll(this.localListAdapter.getItems());
        return items;
    }

    public List<UserVariable> getVariables() {
        List<UserVariable> items = new ArrayList();
        items.addAll(this.globalVarAdapter.getItems());
        items.addAll(this.localVarAdapter.getItems());
        return items;
    }

    public List<UserList> getLists() {
        List<UserList> items = new ArrayList();
        items.addAll(this.globalListAdapter.getItems());
        items.addAll(this.localListAdapter.getItems());
        return items;
    }

    public List<UserData> getSelectedItems() {
        List<UserData> selectedItems = new ArrayList();
        selectedItems.addAll(this.globalVarAdapter.getSelectedItems());
        selectedItems.addAll(this.localVarAdapter.getSelectedItems());
        selectedItems.addAll(this.globalListAdapter.getSelectedItems());
        selectedItems.addAll(this.localListAdapter.getSelectedItems());
        return selectedItems;
    }

    public void setSelection(UserData item, boolean selection) {
        if (item instanceof UserVariable) {
            if (!this.globalVarAdapter.setSelection((UserVariable) item, selection)) {
                this.localVarAdapter.setSelection((UserVariable) item, selection);
            }
        } else if (!this.globalListAdapter.setSelection((UserList) item, selection)) {
            this.localListAdapter.setSelection((UserList) item, selection);
        }
    }

    public int getItemCount() {
        return ((this.globalVarAdapter.getItemCount() + this.localVarAdapter.getItemCount()) + this.globalListAdapter.getItemCount()) + this.localListAdapter.getItemCount();
    }
}
