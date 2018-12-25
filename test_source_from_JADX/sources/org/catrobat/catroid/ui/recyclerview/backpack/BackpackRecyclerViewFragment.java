package org.catrobat.catroid.ui.recyclerview.backpack;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.PluralsRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperCallback;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.utils.ToastUtil;

public abstract class BackpackRecyclerViewFragment<T> extends Fragment implements Callback, SelectionListener, OnItemClickListener<T> {
    protected static final int DELETE = 2;
    protected static final int NONE = 0;
    protected static final int UNPACK = 1;
    protected ActionMode actionMode;
    protected int actionModeType = 0;
    protected ExtendedRVAdapter<T> adapter;
    public boolean hasDetails = false;
    protected View parentView;
    protected RecyclerView recyclerView;
    protected String sharedPreferenceDetailsKey = "";
    protected ItemTouchHelper touchHelper;

    protected abstract void deleteItems(List<T> list);

    @PluralsRes
    protected abstract int getActionModeTitleId(int i);

    @PluralsRes
    protected abstract int getDeleteAlertTitleId();

    protected abstract String getItemName(T t);

    protected abstract void initializeAdapter();

    protected abstract void unpackItems(List<T> list);

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        switch (this.actionModeType) {
            case 0:
                return false;
            case 1:
                mode.setTitle(getString(R.string.am_unpack));
                break;
            case 2:
                mode.setTitle(getString(R.string.am_delete));
                break;
            default:
                break;
        }
        mode.getMenuInflater().inflate(R.menu.context_menu, menu);
        this.adapter.showCheckBoxes = true;
        this.adapter.notifyDataSetChanged();
        return true;
    }

    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() != R.id.confirm) {
            return false;
        }
        handleContextualAction();
        return true;
    }

    public void onDestroyActionMode(ActionMode mode) {
        resetActionModeParameters();
        this.adapter.clearSelection();
    }

    private void handleContextualAction() {
        if (this.adapter.getSelectedItems().isEmpty()) {
            this.actionMode.finish();
            return;
        }
        switch (this.actionModeType) {
            case 0:
                throw new IllegalStateException("ActionModeType not set Correctly");
            case 1:
                unpackItems(this.adapter.getSelectedItems());
                break;
            case 2:
                showDeleteAlert(this.adapter.getSelectedItems());
                break;
            default:
                break;
        }
    }

    protected void resetActionModeParameters() {
        this.actionModeType = 0;
        this.actionMode = null;
        this.adapter.showCheckBoxes = false;
        this.adapter.allowMultiSelection = true;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.parentView = inflater.inflate(R.layout.fragment_list_view, container, false);
        this.recyclerView = (RecyclerView) this.parentView.findViewById(R.id.recycler_view);
        setHasOptionsMenu(true);
        return this.parentView;
    }

    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        initializeAdapter();
    }

    public void onAdapterReady() {
        this.adapter.showDetails = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(this.sharedPreferenceDetailsKey, false);
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.setSelectionListener(this);
        this.adapter.setOnItemClickListener(this);
        this.touchHelper = new ItemTouchHelper(new TouchHelperCallback(this.adapter));
        this.touchHelper.attachToRecyclerView(this.recyclerView);
    }

    public void onResume() {
        super.onResume();
        this.adapter.notifyDataSetChanged();
    }

    public void onStop() {
        super.onStop();
        finishActionMode();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (this.hasDetails) {
            this.adapter.showDetails = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(this.sharedPreferenceDetailsKey, false);
            menu.findItem(R.id.show_details).setTitle(this.adapter.showDetails ? R.string.hide_details : R.string.show_details);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.delete) {
            startActionMode(2);
        } else if (itemId == R.id.show_details) {
            this.adapter.showDetails ^= true;
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(this.sharedPreferenceDetailsKey, this.adapter.showDetails).commit();
            this.adapter.notifyDataSetChanged();
        } else if (itemId != R.id.unpack) {
            return super.onOptionsItemSelected(item);
        } else {
            startActionMode(1);
        }
        return true;
    }

    private void startActionMode(int type) {
        if (this.adapter.getItems().isEmpty()) {
            ToastUtil.showError(getActivity(), (int) R.string.am_empty_list);
            return;
        }
        this.actionModeType = type;
        this.actionMode = getActivity().startActionMode(this);
    }

    public void onSelectionChanged(int selectedItemCnt) {
        this.actionMode.setTitle(getResources().getQuantityString(getActionModeTitleId(this.actionModeType), selectedItemCnt, new Object[]{Integer.valueOf(selectedItemCnt)}));
    }

    protected void finishActionMode() {
        this.adapter.clearSelection();
        setShowProgressBar(false);
        if (this.actionModeType != 0) {
            this.actionMode.finish();
        }
    }

    protected void showDeleteAlert(final List<T> selectedItems) {
        new AlertDialog$Builder(getContext()).setTitle(getResources().getQuantityString(getDeleteAlertTitleId(), selectedItems.size())).setMessage(R.string.dialog_confirm_delete).setPositiveButton(R.string.yes, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                BackpackRecyclerViewFragment.this.deleteItems(selectedItems);
            }
        }).setNegativeButton(R.string.no, null).setCancelable(false).create().show();
    }

    public void onItemClick(final T item) {
        if (this.actionModeType == 0) {
            new AlertDialog$Builder(getContext()).setTitle(getItemName(item)).setItems(new CharSequence[]{getString(R.string.unpack), getString(R.string.delete)}, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            BackpackRecyclerViewFragment.this.unpackItems(new ArrayList(Collections.singletonList(item)));
                            return;
                        case 1:
                            BackpackRecyclerViewFragment.this.showDeleteAlert(new ArrayList(Collections.singletonList(item)));
                            return;
                        default:
                            return;
                    }
                }
            }).show();
        }
    }

    public void setShowProgressBar(boolean show) {
        int i = 8;
        this.parentView.findViewById(R.id.progress_bar).setVisibility(show ? 0 : 8);
        RecyclerView recyclerView = this.recyclerView;
        if (!show) {
            i = 0;
        }
        recyclerView.setVisibility(i);
    }

    public void onItemLongClick(T item, CheckableVH holder) {
        onItemClick(item);
    }
}
