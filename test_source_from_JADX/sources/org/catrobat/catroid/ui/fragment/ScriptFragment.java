package org.catrobat.catroid.ui.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.AllowedAfterDeadEndBrick;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.DeadEndBrick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.NestingBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.commands.CommandFactory;
import org.catrobat.catroid.content.commands.OnFormulaChangedListener;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.adapter.BrickAdapter;
import org.catrobat.catroid.ui.adapter.BrickAdapter$ActionModeEnum;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.dragndrop.BrickListView;
import org.catrobat.catroid.ui.fragment.BrickCategoryFragment.OnCategorySelectedListener;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackActivity;
import org.catrobat.catroid.ui.recyclerview.controller.ScriptController;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.UniqueStringTextWatcher;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;

public class ScriptFragment extends ListFragment implements OnCategorySelectedListener, OnFormulaChangedListener {
    private static final int BACKPACK = 1;
    private static final int COPY = 2;
    private static final int DELETE = 3;
    private static final int ENABLE_DISABLE = 4;
    private static final int NONE = 0;
    public static final String TAG = ScriptFragment.class.getSimpleName();
    private ActionMode actionMode;
    private int actionModeType = 0;
    private BrickAdapter adapter;
    private Callback callback = new C19521();
    private BrickListView listView;
    private Parcelable savedListViewState;
    private ScriptController scriptController = new ScriptController();
    private Script scriptToEdit;
    private Sprite sprite;

    /* renamed from: org.catrobat.catroid.ui.fragment.ScriptFragment$1 */
    class C19521 implements Callback {
        C19521() {
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.context_menu, menu);
            switch (ScriptFragment.this.actionModeType) {
                case 0:
                    ScriptFragment.this.actionMode.finish();
                    return false;
                case 1:
                    ScriptFragment.this.adapter.setActionMode(BrickAdapter$ActionModeEnum.BACKPACK);
                    break;
                case 2:
                case 3:
                    ScriptFragment.this.adapter.setActionMode(BrickAdapter$ActionModeEnum.COPY_DELETE);
                    break;
                case 4:
                    ScriptFragment.this.adapter.setActionMode(BrickAdapter$ActionModeEnum.COMMENT_OUT);
                    ScriptFragment.this.adapter.checkCommentedOutItems();
                    break;
                default:
                    break;
            }
            for (int i = ScriptFragment.this.adapter.listItemCount; i < ScriptFragment.this.adapter.getBrickList().size(); i++) {
                ScriptFragment.this.adapter.getView(i, null, ScriptFragment.this.getListView());
            }
            ScriptFragment.this.adapter.setSelectMode(2);
            ScriptFragment.this.adapter.setCheckboxVisibility();
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            if (ScriptFragment.this.actionModeType != 4) {
                return false;
            }
            menu.findItem(R.id.confirm).setVisible(false);
            return true;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() != R.id.confirm) {
                return false;
            }
            ScriptFragment.this.handleContextualAction();
            return true;
        }

        public void onDestroyActionMode(ActionMode mode) {
            ScriptFragment.this.resetActionModeParameters();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.fragment.ScriptFragment$2 */
    class C19532 implements OnClickListener {
        C19532() {
        }

        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    ScriptFragment.this.startActionMode(1);
                    return;
                case 1:
                    ScriptFragment.this.switchToBackpack();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: org.catrobat.catroid.ui.fragment.ScriptFragment$4 */
    class C19544 implements OnClickListener {
        C19544() {
        }

        public void onClick(DialogInterface dialog, int id) {
            ScriptFragment.this.deleteCheckedBricks();
            ScriptFragment.this.finishActionMode();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.fragment.ScriptFragment$3 */
    class C21213 implements TextInputDialog.OnClickListener {
        C21213() {
        }

        public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
            ScriptFragment.this.packItems(textInput);
        }
    }

    private void handleContextualAction() {
        if (this.adapter.getAmountOfCheckedItems() == 0) {
            this.actionMode.finish();
        }
        switch (this.actionModeType) {
            case 0:
                this.actionMode.finish();
                return;
            case 1:
                showNewScriptGroupDialog();
                return;
            case 2:
                copyBricks();
                return;
            case 3:
                showDeleteAlert();
                return;
            case 4:
                this.actionMode.finish();
                return;
            default:
                return;
        }
    }

    private void resetActionModeParameters() {
        this.adapter.setSelectMode(0);
        this.adapter.clearCheckedItems();
        this.actionModeType = 0;
        registerForContextMenu(this.listView);
        BottomBar.showBottomBar(getActivity());
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void updateActionBarTitle() {
        String currentSceneName = ProjectManager.getInstance().getCurrentlyEditedScene().getName();
        String currentSpriteName = ProjectManager.getInstance().getCurrentSprite().getName();
        if (ProjectManager.getInstance().getCurrentProject().getSceneList().size() == 1) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(currentSpriteName);
            return;
        }
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(currentSceneName);
        stringBuilder.append(": ");
        stringBuilder.append(currentSpriteName);
        supportActionBar.setTitle(stringBuilder.toString());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_script, null);
        this.listView = (BrickListView) view.findViewById(16908298);
        SnackbarUtil.showHintSnackbar(getActivity(), R.string.hint_scripts);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListeners();
        ProjectManager.getInstance().getCurrentProject().getBroadcastMessageContainer().update();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.show_details).setVisible(false);
        menu.findItem(R.id.rename).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public void onResume() {
        super.onResume();
        updateActionBarTitle();
        if (Utils.isExternalStorageAvailable()) {
            if (BackpackListManager.getInstance().isBackpackEmpty()) {
                BackpackListManager.getInstance().loadBackpack();
            }
            BottomBar.showBottomBar(getActivity());
            BottomBar.showPlayButton(getActivity());
            BottomBar.showAddButton(getActivity());
            initListeners();
            if (this.adapter != null) {
                this.adapter.resetAlphas();
            }
            if (this.savedListViewState != null) {
                this.listView.onRestoreInstanceState(this.savedListViewState);
            }
            return;
        }
        ToastUtil.showError(getActivity(), (int) R.string.error_no_writiable_external_storage_available);
    }

    public void onPause() {
        super.onPause();
        ProjectManager projectManager = ProjectManager.getInstance();
        this.savedListViewState = this.listView.onSaveInstanceState();
        if (projectManager.getCurrentlyEditedScene() != null) {
            projectManager.saveProject(getActivity().getApplicationContext());
        }
    }

    public BrickAdapter getAdapter() {
        return this.adapter;
    }

    public BrickListView getListView() {
        return this.listView;
    }

    public void onCategorySelected(String category) {
        getFragmentManager().beginTransaction().add(R.id.fragment_container, AddBrickFragment.newInstance(category, this), AddBrickFragment.ADD_BRICK_FRAGMENT_TAG).addToBackStack(null).commit();
        this.adapter.notifyDataSetChanged();
    }

    public void updateAdapterAfterAddNewBrick(Brick brickToBeAdded) {
        int firstVisibleBrick = this.listView.getFirstVisiblePosition();
        this.adapter.addNewBrick((((this.listView.getLastVisiblePosition() + 1) - firstVisibleBrick) / 2) + firstVisibleBrick, brickToBeAdded, true);
        this.adapter.notifyDataSetChanged();
    }

    private void initListeners() {
        this.sprite = ProjectManager.getInstance().getCurrentSprite();
        if (this.sprite != null) {
            this.adapter = new BrickAdapter(this, this.sprite, this.listView);
            if (ProjectManager.getInstance().getCurrentSprite().getNumberOfScripts() > 0) {
                ProjectManager.getInstance().setCurrentScript(((ScriptBrick) this.adapter.getItem(0)).getScript());
            }
            this.listView.setOnCreateContextMenuListener(this);
            this.listView.setOnDragAndDropListener(this.adapter);
            this.listView.setAdapter(this.adapter);
            registerForContextMenu(this.listView);
        }
    }

    private void showCategoryFragment() {
        BrickCategoryFragment brickCategoryFragment = new BrickCategoryFragment();
        brickCategoryFragment.setBrickAdapter(this.adapter);
        brickCategoryFragment.setOnCategorySelectedListener(this);
        getFragmentManager().beginTransaction().add(R.id.fragment_container, brickCategoryFragment, BrickCategoryFragment.BRICK_CATEGORY_FRAGMENT_TAG).addToBackStack(BrickCategoryFragment.BRICK_CATEGORY_FRAGMENT_TAG).commit();
        SnackbarUtil.showHintSnackbar(getActivity(), R.string.hint_category);
        this.adapter.notifyDataSetChanged();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.listView.isCurrentlyDragging()) {
            this.listView.animateHoveringBrick();
            return true;
        }
        int itemId = item.getItemId();
        if (itemId == R.id.backpack) {
            prepareActionMode(1);
        } else if (itemId == R.id.comment_in_out) {
            prepareActionMode(4);
        } else if (itemId == R.id.copy) {
            prepareActionMode(2);
        } else if (itemId != R.id.delete) {
            return super.onOptionsItemSelected(item);
        } else {
            prepareActionMode(3);
        }
        return true;
    }

    protected void prepareActionMode(int type) {
        if (type != 1) {
            startActionMode(type);
        } else if (BackpackListManager.getInstance().getBackpackedScriptGroups().isEmpty()) {
            startActionMode(1);
        } else if (this.adapter.isEmpty()) {
            switchToBackpack();
        } else {
            showBackpackModeChooser();
        }
    }

    private void startActionMode(int type) {
        if (this.adapter.isEmpty()) {
            ToastUtil.showError(getActivity(), (int) R.string.am_empty_list);
            return;
        }
        this.actionModeType = type;
        this.actionMode = getActivity().startActionMode(this.callback);
        updateActionModeTitle();
        unregisterForContextMenu(this.listView);
        BottomBar.hideBottomBar(getActivity());
    }

    public void finishActionMode() {
        if (this.actionModeType != 0) {
            this.actionMode.finish();
        }
    }

    protected void showBackpackModeChooser() {
        new AlertDialog$Builder(getActivity()).setTitle(R.string.backpack_title).setItems(new CharSequence[]{getString(R.string.pack), getString(R.string.unpack)}, new C19532()).show();
    }

    public void handleAddButton() {
        if (this.listView.isCurrentlyDragging()) {
            this.listView.animateHoveringBrick();
        } else {
            showCategoryFragment();
        }
    }

    public void showNewScriptGroupDialog() {
        Builder builder = new Builder(getContext());
        builder.setHint(getString(R.string.script_group_label)).setTextWatcher(new UniqueStringTextWatcher(BackpackListManager.getInstance().getBackpackedScriptGroups())).setPositiveButton(getString(R.string.ok), new C21213());
        builder.setTitle(R.string.new_group).setNegativeButton(R.string.cancel, null).create().show();
    }

    public void packItems(String name) {
        try {
            this.scriptController.pack(name, this.adapter.getCheckedBricks());
            finishActionMode();
            ToastUtil.showSuccess(getActivity(), getString(R.string.packed_script_group));
            switchToBackpack();
        } catch (CloneNotSupportedException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            finishActionMode();
        }
    }

    private void switchToBackpack() {
        Intent intent = new Intent(getActivity(), BackpackActivity.class);
        intent.putExtra("fragmentPosition", 4);
        startActivity(intent);
    }

    public void onFormulaChanged(FormulaBrick formulaBrick, BrickField brickField, Formula newFormula) {
        CommandFactory.makeChangeFormulaCommand(formulaBrick, brickField, newFormula).execute();
        this.adapter.notifyDataSetChanged();
    }

    private void copyBricks() {
        for (Brick brick : this.adapter.getCheckedBricks()) {
            copyBrick(brick);
            if (brick instanceof ScriptBrick) {
                break;
            }
        }
        this.actionMode.finish();
    }

    private void copyBrick(Brick brick) {
        if (!(brick instanceof NestingBrick) || (!(brick instanceof AllowedAfterDeadEndBrick) && !(brick instanceof DeadEndBrick))) {
            if (brick instanceof ScriptBrick) {
                this.scriptToEdit = ((ScriptBrick) brick).getScript();
                try {
                    this.sprite.addScript(this.scriptToEdit.clone());
                    this.adapter.initBrickList();
                    this.adapter.notifyDataSetChanged();
                } catch (CloneNotSupportedException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            } else if (this.adapter.getBrickList().indexOf(brick) != -1) {
                int newPosition = this.adapter.getCount();
                try {
                    Script scriptList;
                    Brick copiedBrick = brick.clone();
                    if (this.adapter.getUserBrick() != null) {
                        scriptList = ProjectManager.getInstance().getCurrentUserBrick().getDefinitionBrick().getUserScript();
                    } else {
                        scriptList = ProjectManager.getInstance().getCurrentScript();
                    }
                    if (brick instanceof NestingBrick) {
                        NestingBrick nestingBrickCopy = (NestingBrick) copiedBrick;
                        nestingBrickCopy.initialize();
                        for (NestingBrick nestingBrick : nestingBrickCopy.getAllNestingBrickParts()) {
                            scriptList.addBrick((Brick) nestingBrick);
                        }
                    } else {
                        scriptList.addBrick(copiedBrick);
                    }
                    this.adapter.addNewBrick(newPosition, copiedBrick, false);
                    this.adapter.initBrickList();
                    ProjectManager.getInstance().saveProject(getActivity().getApplicationContext());
                    this.adapter.notifyDataSetChanged();
                } catch (CloneNotSupportedException exception) {
                    Log.e(getTag(), "Copying a Brick failed", exception);
                    ToastUtil.showError(getActivity(), (int) R.string.error_copying_brick);
                }
            }
        }
    }

    private void deleteBrick(Brick brick) {
        if (brick instanceof ScriptBrick) {
            this.scriptToEdit = ((ScriptBrick) brick).getScript();
            this.adapter.handleScriptDelete(this.sprite, this.scriptToEdit);
            return;
        }
        int brickId = this.adapter.getBrickList().indexOf(brick);
        if (brickId != -1) {
            this.adapter.removeFromBrickListAndProject(brickId, true);
        }
    }

    private void deleteCheckedBricks() {
        for (Brick brick : this.adapter.getReversedCheckedBrickList()) {
            deleteBrick(brick);
        }
    }

    private void showDeleteAlert() {
        new AlertDialog$Builder(getActivity()).setTitle(getResources().getQuantityString(R.plurals.delete_bricks, this.adapter.getAmountOfCheckedItems())).setMessage(R.string.dialog_confirm_delete).setPositiveButton(R.string.yes, new C19544()).setNegativeButton(R.string.no, null).setCancelable(false).create().show();
    }

    public void updateActionModeTitle() {
        int selectedItemCnt = this.adapter.getAmountOfCheckedItems();
        switch (this.actionModeType) {
            case 1:
                this.actionMode.setTitle(getResources().getQuantityString(R.plurals.am_pack_scripts_title, selectedItemCnt, new Object[]{Integer.valueOf(selectedItemCnt)}));
                return;
            case 2:
                this.actionMode.setTitle(getResources().getQuantityString(R.plurals.am_copy_scripts_title, selectedItemCnt, new Object[]{Integer.valueOf(selectedItemCnt)}));
                return;
            case 3:
                this.actionMode.setTitle(getResources().getQuantityString(R.plurals.am_delete_bricks_title, selectedItemCnt, new Object[]{Integer.valueOf(selectedItemCnt)}));
                return;
            case 4:
                this.actionMode.setTitle(getString(R.string.comment_in_out));
                return;
            default:
                return;
        }
    }
}
