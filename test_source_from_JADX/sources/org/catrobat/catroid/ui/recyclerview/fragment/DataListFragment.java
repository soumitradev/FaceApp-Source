package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.recyclerview.adapter.DataListAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.RenameItemTextWatcher;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.utils.ToastUtil;

public class DataListFragment extends Fragment implements Callback, SelectionListener, OnItemClickListener<UserData> {
    private static final int DELETE = 1;
    private static final int NONE = 0;
    public static final String TAG = DataListFragment.class.getSimpleName();
    private ActionMode actionMode;
    protected int actionModeType = 0;
    private DataListAdapter adapter;
    private FormulaEditorDataInterface formulaEditorDataInterface;
    private RecyclerView recyclerView;

    public interface FormulaEditorDataInterface {
        void onDataItemSelected(UserData userData);

        void onListRenamed(String str, String str2);

        void onVariableRenamed(String str, String str2);
    }

    public void setFormulaEditorDataInterface(FormulaEditorDataInterface formulaEditorDataInterface) {
        this.formulaEditorDataInterface = formulaEditorDataInterface;
    }

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        switch (this.actionModeType) {
            case 0:
                return false;
            case 1:
                mode.setTitle(getString(R.string.am_delete));
                break;
            default:
                break;
        }
        mode.getMenuInflater().inflate(R.menu.context_menu, menu);
        this.adapter.showCheckBoxes(true);
        this.adapter.updateDataSet();
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
                showDeleteAlert(this.adapter.getSelectedItems());
                break;
            default:
                break;
        }
    }

    protected void resetActionModeParameters() {
        this.actionModeType = 0;
        this.actionMode = null;
        this.adapter.showCheckBoxes(false);
        this.adapter.allowMultiSelection = true;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_list_view, container, false);
        this.recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view);
        setHasOptionsMenu(true);
        return parent;
    }

    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        initializeAdapter();
    }

    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.formula_editor_data);
        BottomBar.showBottomBar(getActivity());
        BottomBar.hidePlayButton(getActivity());
    }

    public void onStop() {
        super.onStop();
        finishActionMode();
        BottomBar.hideBottomBar(getActivity());
    }

    private void initializeAdapter() {
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        DataContainer dataContainer = ProjectManager.getInstance().getCurrentlyEditedScene().getDataContainer();
        this.adapter = new DataListAdapter(dataContainer.getProjectUserVariables(), dataContainer.getSpriteUserVariables(currentSprite), dataContainer.getProjectUserLists(), dataContainer.getSpriteUserLists(currentSprite));
        onAdapterReady();
    }

    private void onAdapterReady() {
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.setSelectionListener(this);
        this.adapter.setOnItemClickListener(this);
    }

    public void notifyDataSetChanged() {
        this.adapter.notifyDataSetChanged();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        for (int index = 0; index < menu.size(); index++) {
            menu.getItem(index).setVisible(false);
        }
        menu.findItem(R.id.delete).setVisible(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.delete) {
            return super.onOptionsItemSelected(item);
        }
        startActionMode(1);
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

    protected void finishActionMode() {
        this.adapter.clearSelection();
        if (this.actionModeType != 0) {
            this.actionMode.finish();
        }
    }

    public void showDeleteAlert(final List<UserData> selectedItems) {
        new AlertDialog$Builder(getActivity()).setTitle(R.string.deletion_alert_title).setMessage(R.string.deletion_alert_text).setPositiveButton(R.string.deletion_alert_yes, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DataListFragment.this.deleteItems(selectedItems);
            }
        }).setNegativeButton(R.string.no, null).setCancelable(false).create().show();
    }

    private void deleteItems(List<UserData> selectedItems) {
        finishActionMode();
        for (UserData item : selectedItems) {
            this.adapter.remove(item);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_Items, selectedItems.size(), new Object[]{Integer.valueOf(selectedItems.size())}));
    }

    private void showRenameDialog(List<UserData> selectedItems) {
        final UserData item = (UserData) selectedItems.get(0);
        Builder builder = new Builder(getContext());
        builder.setHint(getString(R.string.data_label)).setText(item.getName()).setTextWatcher(new RenameItemTextWatcher(item, this.adapter.getItems())).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() {
            public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
                DataListFragment.this.renameItem(item, textInput);
            }
        });
        builder.setTitle(R.string.rename_data_dialog).setNegativeButton(R.string.cancel, null).create().show();
    }

    public void renameItem(UserData item, String name) {
        String previousName = item.getName();
        item.setName(name);
        this.adapter.updateDataSet();
        finishActionMode();
        if (item instanceof UserVariable) {
            this.formulaEditorDataInterface.onVariableRenamed(previousName, name);
        } else {
            this.formulaEditorDataInterface.onListRenamed(previousName, name);
        }
    }

    public void onSelectionChanged(int selectedItemCnt) {
        this.actionMode.setTitle(getResources().getQuantityString(R.plurals.am_delete_user_data_items_title, selectedItemCnt, new Object[]{Integer.valueOf(selectedItemCnt)}));
    }

    public void onItemClick(UserData item) {
        if (this.actionModeType == 0) {
            this.formulaEditorDataInterface.onDataItemSelected(item);
            getFragmentManager().popBackStack();
        }
    }

    public void onItemLongClick(final UserData item, CheckableVH holder) {
        new AlertDialog$Builder(getActivity()).setItems(new CharSequence[]{getString(R.string.delete), getString(R.string.rename)}, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        DataListFragment.this.showDeleteAlert(new ArrayList(Collections.singletonList(item)));
                        return;
                    case 1:
                        DataListFragment.this.showRenameDialog(new ArrayList(Collections.singletonList(item)));
                        return;
                    default:
                        dialog.dismiss();
                        return;
                }
            }
        }).show();
    }
}
