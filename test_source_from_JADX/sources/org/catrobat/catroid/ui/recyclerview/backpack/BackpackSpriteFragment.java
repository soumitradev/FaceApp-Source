package org.catrobat.catroid.ui.recyclerview.backpack;

import android.support.annotation.PluralsRes;
import android.util.Log;
import java.io.IOException;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.SpriteAdapter;
import org.catrobat.catroid.ui.recyclerview.controller.SpriteController;
import org.catrobat.catroid.utils.ToastUtil;

public class BackpackSpriteFragment extends BackpackRecyclerViewFragment<Sprite> {
    public static final String TAG = BackpackSpriteFragment.class.getSimpleName();
    private SpriteController spriteController = new SpriteController();

    protected void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SPRITES_PREFERENCE_KEY;
        this.hasDetails = true;
        this.adapter = new SpriteAdapter(BackpackListManager.getInstance().getSprites());
        onAdapterReady();
    }

    protected void unpackItems(List<Sprite> selectedItems) {
        setShowProgressBar(true);
        Scene dstScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        int unpackedItemCnt = 0;
        for (Sprite item : selectedItems) {
            try {
                dstScene.getSpriteList().add(this.spriteController.unpack(item, dstScene));
                unpackedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (unpackedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_sprites, unpackedItemCnt, new Object[]{Integer.valueOf(unpackedItemCnt)}));
            getActivity().finish();
        }
        finishActionMode();
    }

    @PluralsRes
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_sprites;
    }

    protected void deleteItems(List<Sprite> selectedItems) {
        setShowProgressBar(true);
        for (Sprite item : selectedItems) {
            this.spriteController.delete(item);
            this.adapter.remove(item);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_sprites, selectedItems.size(), new Object[]{Integer.valueOf(selectedItems.size())}));
        BackpackListManager.getInstance().saveBackpack();
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            getActivity().finish();
        }
    }

    protected String getItemName(Sprite item) {
        return item.getName();
    }

    @PluralsRes
    protected int getActionModeTitleId(int actionModeType) {
        switch (actionModeType) {
            case 1:
                return R.plurals.am_unpack_sprites_title;
            case 2:
                return R.plurals.am_delete_sprites_title;
            default:
                throw new IllegalStateException("ActionModeType not set correctly");
        }
    }
}
