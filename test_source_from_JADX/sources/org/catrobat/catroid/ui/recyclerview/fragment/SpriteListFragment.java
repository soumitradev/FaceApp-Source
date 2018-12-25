package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.PluralsRes;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.GroupSprite;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.SpriteAttributesActivity;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.MultiViewSpriteAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperAdapterInterface;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperCallback;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackActivity;
import org.catrobat.catroid.ui.recyclerview.controller.SpriteController;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.NewItemTextWatcher;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.ToastUtil;

public class SpriteListFragment extends RecyclerViewFragment<Sprite> {
    public static final String TAG = SpriteListFragment.class.getSimpleName();
    private SpriteController spriteController = new SpriteController();

    /* renamed from: org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment$1 */
    class C21321 implements OnClickListener {
        C21321() {
        }

        public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
            SpriteListFragment.this.adapter.add(new GroupSprite(textInput));
        }
    }

    class MultiViewTouchHelperCallback extends TouchHelperCallback {
        MultiViewTouchHelperCallback(TouchHelperAdapterInterface adapterInterface) {
            super(adapterInterface);
        }

        public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState == 0) {
                Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
                List<Sprite> items = SpriteListFragment.this.adapter.getItems();
                for (Sprite sprite : items) {
                    if (!(sprite instanceof GroupSprite)) {
                        if (sprite.toBeConverted()) {
                            items.set(items.indexOf(sprite), SpriteListFragment.this.spriteController.convert(sprite, currentScene));
                        }
                    }
                }
                for (Sprite sprite2 : items) {
                    if (sprite2 instanceof GroupSprite) {
                        GroupSprite groupSprite = (GroupSprite) sprite2;
                        groupSprite.setCollapsed(groupSprite.getCollapsed());
                    }
                }
                SpriteListFragment.this.adapter.notifyDataSetChanged();
            }
        }
    }

    boolean shouldShowEmptyView() {
        return this.adapter.getItemCount() == 1;
    }

    public void onResume() {
        String title;
        super.onResume();
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        if (currentProject.getSceneList().size() < 2) {
            title = currentProject.getName();
        } else {
            Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(currentProject.getName());
            stringBuilder.append(": ");
            stringBuilder.append(currentScene.getName());
            title = stringBuilder.toString();
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    public void onAdapterReady() {
        super.onAdapterReady();
        this.touchHelper = new ItemTouchHelper(new MultiViewTouchHelperCallback(this.adapter));
        this.touchHelper.attachToRecyclerView(this.recyclerView);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.new_group).setVisible(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.new_group) {
            super.onOptionsItemSelected(item);
        } else {
            showNewGroupDialog();
        }
        return true;
    }

    private void showNewGroupDialog() {
        Builder builder = new Builder(getContext());
        builder.setHint(getString(R.string.sprite_group_name_label)).setTextWatcher(new NewItemTextWatcher(this.adapter.getItems())).setPositiveButton(getString(R.string.ok), new C21321());
        builder.setTitle(R.string.new_group).setNegativeButton(R.string.cancel, null).create().show();
    }

    protected void initializeAdapter() {
        SnackbarUtil.showHintSnackbar(getActivity(), R.string.hint_objects);
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SPRITES_PREFERENCE_KEY;
        this.adapter = new MultiViewSpriteAdapter(ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList());
        this.emptyView.setText(R.string.fragment_sprite_text_description);
        onAdapterReady();
    }

    protected void packItems(List<Sprite> selectedItems) {
        setShowProgressBar(true);
        int packedItemCnt = 0;
        for (Sprite item : selectedItems) {
            try {
                BackpackListManager.getInstance().getSprites().add(this.spriteController.pack(item));
                BackpackListManager.getInstance().saveBackpack();
                packedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (packedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.packed_sprites, packedItemCnt, new Object[]{Integer.valueOf(packedItemCnt)}));
            switchToBackpack();
        }
        finishActionMode();
    }

    protected boolean isBackpackEmpty() {
        return BackpackListManager.getInstance().getSprites().isEmpty();
    }

    protected void switchToBackpack() {
        Intent intent = new Intent(getActivity(), BackpackActivity.class);
        intent.putExtra("fragmentPosition", 1);
        startActivity(intent);
    }

    protected void copyItems(List<Sprite> selectedItems) {
        setShowProgressBar(true);
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        int copiedItemCnt = 0;
        for (Sprite item : selectedItems) {
            try {
                this.adapter.add(this.spriteController.copy(item, currentScene, currentScene));
                copiedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (copiedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.copied_sprites, copiedItemCnt, new Object[]{Integer.valueOf(copiedItemCnt)}));
        }
        finishActionMode();
    }

    @PluralsRes
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_sprites;
    }

    protected void deleteItems(List<Sprite> selectedItems) {
        setShowProgressBar(true);
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        for (Sprite item : selectedItems) {
            if (item instanceof GroupSprite) {
                for (Sprite sprite : ((GroupSprite) item).getGroupItems()) {
                    sprite.setConvertToSingleSprite(true);
                    this.adapter.getItems().set(this.adapter.getItems().indexOf(sprite), this.spriteController.convert(sprite, currentScene));
                }
                this.adapter.notifyDataSetChanged();
            }
            this.spriteController.delete(item, currentScene);
            this.adapter.remove(item);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_sprites, selectedItems.size(), new Object[]{Integer.valueOf(selectedItems.size())}));
        finishActionMode();
    }

    protected int getRenameDialogTitle() {
        return R.string.rename_sprite_dialog;
    }

    protected int getRenameDialogHint() {
        return R.string.sprite_name_label;
    }

    @PluralsRes
    protected int getActionModeTitleId(int actionModeType) {
        switch (actionModeType) {
            case 1:
                return R.plurals.am_pack_sprites_title;
            case 2:
                return R.plurals.am_copy_sprites_title;
            case 3:
                return R.plurals.am_delete_sprites_title;
            case 4:
                return R.plurals.am_rename_sprites_title;
            default:
                throw new IllegalStateException("ActionModeType not set correctly");
        }
    }

    public void onItemClick(Sprite item) {
        if (item instanceof GroupSprite) {
            GroupSprite groupSprite = (GroupSprite) item;
            groupSprite.setCollapsed(groupSprite.getCollapsed() ^ 1);
            this.adapter.notifyDataSetChanged();
        } else if (this.actionModeType == 0) {
            ProjectManager.getInstance().setCurrentSprite(item);
            startActivity(new Intent(getActivity(), SpriteAttributesActivity.class));
        }
    }

    public void onItemLongClick(final Sprite item, CheckableVH holder) {
        if (item instanceof GroupSprite) {
            new AlertDialog$Builder(getContext()).setTitle(item.getName()).setItems(new CharSequence[]{getString(R.string.delete), getString(R.string.rename)}, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            SpriteListFragment.this.showDeleteAlert(new ArrayList(Collections.singletonList(item)));
                            return;
                        case 1:
                            SpriteListFragment.this.showRenameDialog(new ArrayList(Collections.singletonList(item)));
                            return;
                        default:
                            dialog.dismiss();
                            return;
                    }
                }
            }).show();
            return;
        }
        super.onItemLongClick((Nameable) item, holder);
    }
}
