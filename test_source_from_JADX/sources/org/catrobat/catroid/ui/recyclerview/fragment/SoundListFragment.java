package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.Intent;
import android.support.annotation.PluralsRes;
import android.util.Log;
import java.io.IOException;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.pocketmusic.PocketMusicActivity;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.SoundAdapter;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackActivity;
import org.catrobat.catroid.ui.recyclerview.controller.SoundController;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.ToastUtil;

public class SoundListFragment extends RecyclerViewFragment<SoundInfo> {
    public static final String TAG = SoundListFragment.class.getSimpleName();
    private SoundController soundController = new SoundController();

    protected void initializeAdapter() {
        SnackbarUtil.showHintSnackbar(getActivity(), R.string.hint_sounds);
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SOUNDS_PREFERENCE_KEY;
        this.adapter = new SoundAdapter(ProjectManager.getInstance().getCurrentSprite().getSoundList());
        this.emptyView.setText(R.string.fragment_sound_text_description);
        onAdapterReady();
    }

    protected void packItems(List<SoundInfo> selectedItems) {
        setShowProgressBar(true);
        int packedItemCnt = 0;
        for (SoundInfo item : selectedItems) {
            try {
                BackpackListManager.getInstance().getBackpackedSounds().add(this.soundController.pack(item));
                BackpackListManager.getInstance().saveBackpack();
                packedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (packedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.packed_sounds, packedItemCnt, new Object[]{Integer.valueOf(packedItemCnt)}));
            switchToBackpack();
        }
        finishActionMode();
    }

    protected boolean isBackpackEmpty() {
        return BackpackListManager.getInstance().getBackpackedSounds().isEmpty();
    }

    protected void switchToBackpack() {
        Intent intent = new Intent(getActivity(), BackpackActivity.class);
        intent.putExtra("fragmentPosition", 3);
        startActivity(intent);
    }

    protected void copyItems(List<SoundInfo> selectedItems) {
        setShowProgressBar(true);
        int copiedItemCnt = 0;
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        for (SoundInfo item : selectedItems) {
            try {
                this.adapter.add(this.soundController.copy(item, currentScene, currentSprite));
                copiedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (copiedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.copied_sounds, copiedItemCnt, new Object[]{Integer.valueOf(copiedItemCnt)}));
        }
        finishActionMode();
    }

    @PluralsRes
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_sounds;
    }

    protected void deleteItems(List<SoundInfo> selectedItems) {
        setShowProgressBar(true);
        for (SoundInfo item : selectedItems) {
            try {
                this.soundController.delete(item);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            this.adapter.remove(item);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_sounds, selectedItems.size(), new Object[]{Integer.valueOf(selectedItems.size())}));
        finishActionMode();
    }

    protected int getRenameDialogTitle() {
        return R.string.rename_sound_dialog;
    }

    protected int getRenameDialogHint() {
        return R.string.sound_name_label;
    }

    @PluralsRes
    protected int getActionModeTitleId(int actionModeType) {
        switch (actionModeType) {
            case 1:
                return R.plurals.am_pack_sounds_title;
            case 2:
                return R.plurals.am_copy_sounds_title;
            case 3:
                return R.plurals.am_delete_sounds_title;
            case 4:
                return R.plurals.am_rename_sounds_title;
            default:
                throw new IllegalStateException("ActionModeType not set correctly");
        }
    }

    public void onItemClick(SoundInfo item) {
        if (this.actionModeType == 0 && item.getFile().getName().matches(".*MUS-.*\\.midi")) {
            Intent intent = new Intent(getActivity(), PocketMusicActivity.class);
            intent.putExtra("title", item.getName());
            intent.putExtra(PocketMusicActivity.ABSOLUTE_FILE_PATH, item.getFile().getAbsolutePath());
            startActivity(intent);
        }
    }
}
