package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperCallback;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.RenameItemTextWatcher;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.utils.ToastUtil;

public abstract class RecyclerViewFragment<T extends Nameable> extends Fragment implements Callback, SelectionListener, OnItemClickListener<T> {
    protected static final int BACKPACK = 1;
    protected static final int COPY = 2;
    protected static final int DELETE = 3;
    protected static final int NONE = 0;
    protected static final int RENAME = 4;
    protected ActionMode actionMode;
    protected int actionModeType = 0;
    protected ExtendedRVAdapter<T> adapter;
    protected TextView emptyView;
    protected AdapterDataObserver observer = new C21301();
    protected View parentView;
    protected RecyclerView recyclerView;
    protected String sharedPreferenceDetailsKey = "";
    protected ItemTouchHelper touchHelper;
    protected UniqueNameProvider uniqueNameProvider = new UniqueNameProvider();

    /* renamed from: org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment$2 */
    class C20032 implements OnClickListener {
        C20032() {
        }

        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    RecyclerViewFragment.this.startActionMode(1);
                    return;
                case 1:
                    RecyclerViewFragment.this.switchToBackpack();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment$1 */
    class C21301 extends AdapterDataObserver {
        C21301() {
        }

        public void onChanged() {
            super.onChanged();
            RecyclerViewFragment.this.setShowEmptyView(RecyclerViewFragment.this.shouldShowEmptyView());
        }
    }

    protected abstract void copyItems(List<T> list);

    protected abstract void deleteItems(List<T> list);

    @PluralsRes
    protected abstract int getActionModeTitleId(int i);

    @PluralsRes
    protected abstract int getDeleteAlertTitleId();

    @StringRes
    protected abstract int getRenameDialogHint();

    @StringRes
    protected abstract int getRenameDialogTitle();

    protected abstract void initializeAdapter();

    protected abstract boolean isBackpackEmpty();

    protected abstract void packItems(List<T> list);

    protected abstract void switchToBackpack();

    boolean shouldShowEmptyView() {
        return this.adapter.getItemCount() == 0;
    }

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        switch (this.actionModeType) {
            case 0:
                return false;
            case 1:
                mode.setTitle(getString(R.string.am_backpack));
                break;
            case 2:
                mode.setTitle(getString(R.string.am_copy));
                break;
            case 3:
                mode.setTitle(getString(R.string.am_delete));
                break;
            case 4:
                this.adapter.allowMultiSelection = false;
                mode.setTitle(getString(R.string.am_rename));
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
        updateSelectionToggle(menu.findItem(R.id.toggle_selection));
        return true;
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.confirm) {
            handleContextualAction();
        } else if (itemId != R.id.toggle_selection) {
            return false;
        } else {
            this.adapter.toggleSelection();
            updateSelectionToggle(item);
        }
        return true;
    }

    public void onDestroyActionMode(ActionMode mode) {
        resetActionModeParameters();
        this.adapter.clearSelection();
        BottomBar.showBottomBar(getActivity());
    }

    private void handleContextualAction() {
        if (this.adapter.getSelectedItems().isEmpty()) {
            this.actionMode.finish();
            return;
        }
        switch (this.actionModeType) {
            case 0:
                throw new IllegalStateException("ActionModeType not set correctly");
            case 1:
                packItems(this.adapter.getSelectedItems());
                break;
            case 2:
                copyItems(this.adapter.getSelectedItems());
                break;
            case 3:
                showDeleteAlert(this.adapter.getSelectedItems());
                break;
            case 4:
                showRenameDialog(this.adapter.getSelectedItems());
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.parentView = inflater.inflate(R.layout.fragment_list_view, container, false);
        this.recyclerView = (RecyclerView) this.parentView.findViewById(R.id.recycler_view);
        this.emptyView = (TextView) this.parentView.findViewById(R.id.empty_view);
        setShowProgressBar(true);
        setHasOptionsMenu(true);
        return this.parentView;
    }

    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        if (!getActivity().isFinishing()) {
            initializeAdapter();
        }
    }

    public void onAdapterReady() {
        this.adapter.showDetails = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(this.sharedPreferenceDetailsKey, false);
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.setSelectionListener(this);
        this.adapter.setOnItemClickListener(this);
        this.touchHelper = new ItemTouchHelper(new TouchHelperCallback(this.adapter));
        this.touchHelper.attachToRecyclerView(this.recyclerView);
        setShowProgressBar(false);
    }

    public void onResume() {
        super.onResume();
        setShowProgressBar(false);
        BackpackListManager.getInstance().loadBackpack();
        this.adapter.notifyDataSetChanged();
        this.adapter.registerAdapterDataObserver(this.observer);
        setShowEmptyView(shouldShowEmptyView());
    }

    public void onPause() {
        super.onPause();
        ProjectManager.getInstance().saveProject(getActivity());
        this.adapter.unregisterAdapterDataObserver(this.observer);
    }

    public void onStop() {
        super.onStop();
        finishActionMode();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Context context = getActivity();
        if (context != null) {
            this.adapter.showDetails = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(this.sharedPreferenceDetailsKey, false);
            menu.findItem(R.id.show_details).setTitle(this.adapter.showDetails ? R.string.hide_details : R.string.show_details);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backpack:
                prepareActionMode(1);
                break;
            case R.id.copy:
                prepareActionMode(2);
                break;
            case R.id.delete:
                prepareActionMode(3);
                break;
            case R.id.rename:
                prepareActionMode(4);
                break;
            case R.id.show_details:
                this.adapter.showDetails ^= true;
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(this.sharedPreferenceDetailsKey, this.adapter.showDetails).commit();
                this.adapter.notifyDataSetChanged();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    protected void prepareActionMode(int type) {
        if (type != 1) {
            startActionMode(type);
        } else if (isBackpackEmpty()) {
            startActionMode(1);
        } else if (this.adapter.getItems().isEmpty()) {
            switchToBackpack();
        } else {
            showBackpackModeChooser();
        }
    }

    private void startActionMode(int type) {
        if (this.adapter.getItems().isEmpty()) {
            ToastUtil.showError(getActivity(), (int) R.string.am_empty_list);
            resetActionModeParameters();
            return;
        }
        this.actionModeType = type;
        this.actionMode = getActivity().startActionMode(this);
        BottomBar.hideBottomBar(getActivity());
    }

    public void onSelectionChanged(int selectedItemCnt) {
        updateSelectionToggle(this.actionMode.getMenu().findItem(R.id.toggle_selection));
        this.actionMode.setTitle(getResources().getQuantityString(getActionModeTitleId(this.actionModeType), selectedItemCnt, new Object[]{Integer.valueOf(selectedItemCnt)}));
    }

    protected void updateSelectionToggle(MenuItem selectionToggle) {
        if (this.adapter.allowMultiSelection) {
            selectionToggle.setVisible(true);
            if (this.adapter.getSelectedItems().size() == this.adapter.getSelectableItemCount()) {
                selectionToggle.setTitle(R.string.deselect_all);
            } else {
                selectionToggle.setTitle(R.string.select_all);
            }
        }
    }

    protected void finishActionMode() {
        this.adapter.clearSelection();
        setShowProgressBar(false);
        if (this.actionModeType != 0) {
            this.actionMode.finish();
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

    void setShowEmptyView(boolean visible) {
        this.emptyView.setVisibility(visible ? 0 : 8);
    }

    public void onItemLongClick(T t, CheckableVH holder) {
        this.touchHelper.startDrag(holder);
    }

    public void notifyDataSetChanged() {
        this.adapter.notifyDataSetChanged();
    }

    protected void showBackpackModeChooser() {
        new AlertDialog$Builder(getContext()).setTitle(R.string.backpack_title).setItems(new CharSequence[]{getString(R.string.pack), getString(R.string.unpack)}, new C20032()).show();
    }

    protected void showDeleteAlert(final List<T> selectedItems) {
        new AlertDialog$Builder(getContext()).setTitle(getResources().getQuantityString(getDeleteAlertTitleId(), selectedItems.size())).setMessage(R.string.dialog_confirm_delete).setPositiveButton(R.string.yes, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                RecyclerViewFragment.this.deleteItems(selectedItems);
            }
        }).setNegativeButton(R.string.no, null).setCancelable(false).create().show();
    }

    protected void showRenameDialog(List<T> selectedItems) {
        final Nameable item = (Nameable) selectedItems.get(0);
        Builder builder = new Builder(getContext());
        builder.setHint(getString(getRenameDialogHint())).setText(item.getName()).setTextWatcher(new RenameItemTextWatcher(item, this.adapter.getItems())).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() {
            public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
                RecyclerViewFragment.this.renameItem(item, textInput);
            }
        });
        builder.setTitle(getRenameDialogTitle()).setNegativeButton(R.string.cancel, null).create().show();
    }

    protected void renameItem(T item, String newName) {
        item.setName(newName);
        finishActionMode();
    }
}
