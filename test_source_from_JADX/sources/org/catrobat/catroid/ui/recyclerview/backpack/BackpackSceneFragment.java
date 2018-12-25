package org.catrobat.catroid.ui.recyclerview.backpack;

import android.support.annotation.PluralsRes;
import android.util.Log;
import java.io.IOException;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.SceneAdapter;
import org.catrobat.catroid.ui.recyclerview.controller.SceneController;
import org.catrobat.catroid.utils.ToastUtil;

public class BackpackSceneFragment extends BackpackRecyclerViewFragment<Scene> {
    public static final String TAG = BackpackSceneFragment.class.getSimpleName();
    private SceneController sceneController = new SceneController();

    protected void initializeAdapter() {
        List<Scene> items = BackpackListManager.getInstance().getScenes();
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SCENES_PREFERENCE_KEY;
        this.hasDetails = true;
        this.adapter = new SceneAdapter(items);
        onAdapterReady();
    }

    protected void unpackItems(List<Scene> selectedItems) {
        setShowProgressBar(true);
        Project dstProject = ProjectManager.getInstance().getCurrentProject();
        int unpackedItemCnt = 0;
        for (Scene item : selectedItems) {
            try {
                dstProject.addScene(this.sceneController.unpack(item, dstProject));
                unpackedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (unpackedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_scenes, unpackedItemCnt, new Object[]{Integer.valueOf(unpackedItemCnt)}));
            getActivity().finish();
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
        BackpackListManager.getInstance().saveBackpack();
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            getActivity().finish();
        }
    }

    protected String getItemName(Scene item) {
        return item.getName();
    }

    @PluralsRes
    protected int getActionModeTitleId(int actionModeType) {
        switch (actionModeType) {
            case 1:
                return R.plurals.am_unpack_scenes_title;
            case 2:
                return R.plurals.am_delete_scenes_title;
            default:
                throw new IllegalStateException("ActionModeType not set correctly");
        }
    }
}
