package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.Intent;
import android.support.annotation.PluralsRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import java.io.IOException;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.SceneAdapter;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackActivity;
import org.catrobat.catroid.ui.recyclerview.controller.SceneController;
import org.catrobat.catroid.utils.ToastUtil;

public class SceneListFragment extends RecyclerViewFragment<Scene> {
    public static final String TAG = SceneListFragment.class.getSimpleName();
    private SceneController sceneController = new SceneController();

    public void onResume() {
        super.onResume();
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        if (currentProject.getSceneList().size() < 2) {
            switchToSpriteListFragment();
        }
        ProjectManager.getInstance().setCurrentlyEditedScene(currentProject.getDefaultScene());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(currentProject.getName());
    }

    private void switchToSpriteListFragment() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpriteListFragment(), SpriteListFragment.TAG).commit();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.new_group).setVisible(false);
        menu.findItem(R.id.new_scene).setVisible(false);
    }

    protected void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SCENES_PREFERENCE_KEY;
        this.adapter = new SceneAdapter(ProjectManager.getInstance().getCurrentProject().getSceneList());
        onAdapterReady();
    }

    protected void packItems(List<Scene> selectedItems) {
        setShowProgressBar(true);
        int packedItemCnt = 0;
        for (Scene item : selectedItems) {
            try {
                BackpackListManager.getInstance().getScenes().add(this.sceneController.pack(item));
                BackpackListManager.getInstance().saveBackpack();
                packedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (packedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.packed_scenes, packedItemCnt, new Object[]{Integer.valueOf(packedItemCnt)}));
            switchToBackpack();
        }
        finishActionMode();
    }

    protected boolean isBackpackEmpty() {
        return BackpackListManager.getInstance().getScenes().isEmpty();
    }

    protected void switchToBackpack() {
        Intent intent = new Intent(getActivity(), BackpackActivity.class);
        intent.putExtra("fragmentPosition", 0);
        startActivity(intent);
    }

    protected void copyItems(List<Scene> selectedItems) {
        setShowProgressBar(true);
        int copiedItemCnt = 0;
        for (Scene item : selectedItems) {
            try {
                this.adapter.add(this.sceneController.copy(item, ProjectManager.getInstance().getCurrentProject()));
                copiedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (copiedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.copied_scenes, copiedItemCnt, new Object[]{Integer.valueOf(copiedItemCnt)}));
        }
        finishActionMode();
    }

    @PluralsRes
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_scenes;
    }

    protected void deleteItems(List<Scene> selectedItems) {
        setShowProgressBar(true);
        for (Scene item : selectedItems) {
            try {
                this.sceneController.delete(item);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            this.adapter.remove(item);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_scenes, selectedItems.size(), new Object[]{Integer.valueOf(selectedItems.size())}));
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            createEmptySceneWithDefaultName();
        }
        if (ProjectManager.getInstance().getCurrentProject().getSceneList().size() < 2) {
            switchToSpriteListFragment();
        }
    }

    private void createEmptySceneWithDefaultName() {
        setShowProgressBar(true);
        Scene scene = new Scene(getString(R.string.default_scene_name, new Object[]{Integer.valueOf(1)}), ProjectManager.getInstance().getCurrentProject());
        Sprite backgroundSprite = new Sprite(getString(R.string.background));
        backgroundSprite.look.setZIndex(0);
        scene.addSprite(backgroundSprite);
        this.adapter.add(scene);
        setShowProgressBar(false);
    }

    protected int getRenameDialogTitle() {
        return R.string.rename_scene_dialog;
    }

    protected int getRenameDialogHint() {
        return R.string.scene_name_label;
    }

    public void renameItem(Scene item, String name) {
        if (!item.getName().equals(name)) {
            if (this.sceneController.rename(item, name)) {
                ProjectManager.getInstance().saveProject(getActivity());
            } else {
                ToastUtil.showError(getActivity(), (int) R.string.error_rename_scene);
            }
        }
        finishActionMode();
    }

    @PluralsRes
    protected int getActionModeTitleId(int actionModeType) {
        switch (actionModeType) {
            case 1:
                return R.plurals.am_pack_scenes_title;
            case 2:
                return R.plurals.am_copy_scenes_title;
            case 3:
                return R.plurals.am_delete_scenes_title;
            case 4:
                return R.plurals.am_rename_scenes_title;
            default:
                throw new IllegalStateException("ActionModeType not set correctly");
        }
    }

    public void onItemClick(Scene item) {
        if (this.actionModeType == 0) {
            ProjectManager.getInstance().setCurrentlyEditedScene(item);
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpriteListFragment(), SpriteListFragment.TAG).addToBackStack(SpriteListFragment.TAG).commit();
        }
    }
}
