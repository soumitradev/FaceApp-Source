package org.catrobat.catroid.ui.recyclerview.backpack;

import android.support.annotation.PluralsRes;
import android.util.Log;
import java.io.IOException;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.SoundAdapter;
import org.catrobat.catroid.ui.recyclerview.controller.SoundController;
import org.catrobat.catroid.utils.ToastUtil;

public class BackpackSoundFragment extends BackpackRecyclerViewFragment<SoundInfo> {
    public static final String TAG = BackpackSoundFragment.class.getSimpleName();
    private SoundController soundController = new SoundController();

    protected void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SOUNDS_PREFERENCE_KEY;
        this.hasDetails = true;
        this.adapter = new SoundAdapter(BackpackListManager.getInstance().getBackpackedSounds());
        onAdapterReady();
    }

    protected void unpackItems(List<SoundInfo> selectedItems) {
        setShowProgressBar(true);
        Sprite dstSprite = ProjectManager.getInstance().getCurrentSprite();
        int unpackedItemCnt = 0;
        for (SoundInfo item : selectedItems) {
            try {
                dstSprite.getSoundList().add(this.soundController.unpack(item, ProjectManager.getInstance().getCurrentlyEditedScene(), dstSprite));
                unpackedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (unpackedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_sounds, unpackedItemCnt, new Object[]{Integer.valueOf(unpackedItemCnt)}));
            getActivity().finish();
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
        BackpackListManager.getInstance().saveBackpack();
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            getActivity().finish();
        }
    }

    public String getItemName(SoundInfo item) {
        return item.getName();
    }

    @PluralsRes
    protected int getActionModeTitleId(int actionModeType) {
        switch (actionModeType) {
            case 1:
                return R.plurals.am_unpack_sounds_title;
            case 2:
                return R.plurals.am_delete_sounds_title;
            default:
                throw new IllegalStateException("ActionModeType not set correctly");
        }
    }
}
