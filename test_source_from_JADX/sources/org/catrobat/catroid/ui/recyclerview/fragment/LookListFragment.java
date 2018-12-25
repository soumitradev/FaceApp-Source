package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.PluralsRes;
import android.util.Log;
import java.io.IOException;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.LookAdapter;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackActivity;
import org.catrobat.catroid.ui.recyclerview.controller.LookController;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.ToastUtil;

public class LookListFragment extends RecyclerViewFragment<LookData> {
    public static final String TAG = LookListFragment.class.getSimpleName();
    private LookController lookController = new LookController();

    protected void initializeAdapter() {
        SnackbarUtil.showHintSnackbar(getActivity(), R.string.hint_looks);
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_LOOKS_PREFERENCE_KEY;
        this.adapter = new LookAdapter(ProjectManager.getInstance().getCurrentSprite().getLookList());
        this.emptyView.setText(R.string.fragment_look_text_description);
        onAdapterReady();
    }

    protected void packItems(List<LookData> selectedItems) {
        setShowProgressBar(true);
        int packedItemCnt = 0;
        for (LookData item : selectedItems) {
            try {
                BackpackListManager.getInstance().getBackpackedLooks().add(this.lookController.pack(item));
                BackpackListManager.getInstance().saveBackpack();
                packedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (packedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.packed_looks, packedItemCnt, new Object[]{Integer.valueOf(packedItemCnt)}));
            switchToBackpack();
        }
        finishActionMode();
    }

    protected boolean isBackpackEmpty() {
        return BackpackListManager.getInstance().getBackpackedLooks().isEmpty();
    }

    protected void switchToBackpack() {
        Intent intent = new Intent(getActivity(), BackpackActivity.class);
        intent.putExtra("fragmentPosition", 2);
        startActivity(intent);
    }

    protected void copyItems(List<LookData> selectedItems) {
        setShowProgressBar(true);
        int copiedItemCnt = 0;
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        for (LookData item : selectedItems) {
            try {
                this.adapter.add(this.lookController.copy(item, currentScene, currentSprite));
                copiedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (copiedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.copied_looks, copiedItemCnt, new Object[]{Integer.valueOf(copiedItemCnt)}));
        }
        finishActionMode();
    }

    @PluralsRes
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_looks;
    }

    protected void deleteItems(List<LookData> selectedItems) {
        setShowProgressBar(true);
        for (LookData item : selectedItems) {
            try {
                this.lookController.delete(item);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            this.adapter.remove(item);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_looks, selectedItems.size(), new Object[]{Integer.valueOf(selectedItems.size())}));
        finishActionMode();
    }

    protected int getRenameDialogTitle() {
        return R.string.rename_look_dialog;
    }

    protected int getRenameDialogHint() {
        return R.string.look_name_label;
    }

    @PluralsRes
    protected int getActionModeTitleId(int actionModeType) {
        switch (actionModeType) {
            case 1:
                return R.plurals.am_pack_looks_title;
            case 2:
                return R.plurals.am_copy_looks_title;
            case 3:
                return R.plurals.am_delete_looks_title;
            case 4:
                return R.plurals.am_rename_looks_title;
            default:
                throw new IllegalStateException("ActionModeType not set correctly");
        }
    }

    public void onItemClick(LookData item) {
        if (this.actionModeType == 0) {
            item.invalidateThumbnailBitmap();
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(new ComponentName(getActivity(), Constants.POCKET_PAINT_INTENT_ACTIVITY_NAME));
            Bundle bundle = new Bundle();
            bundle.putString(Constants.EXTRA_PICTURE_PATH_POCKET_PAINT, item.getFile().getAbsolutePath());
            intent.putExtras(bundle);
            intent.addCategory("android.intent.category.LAUNCHER");
            startActivity(intent);
        }
    }
}
