package org.catrobat.catroid.ui.recyclerview.backpack;

import android.support.annotation.PluralsRes;
import android.util.Log;
import java.io.IOException;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.LookAdapter;
import org.catrobat.catroid.ui.recyclerview.controller.LookController;
import org.catrobat.catroid.utils.ToastUtil;

public class BackpackLookFragment extends BackpackRecyclerViewFragment<LookData> {
    public static final String TAG = BackpackLookFragment.class.getSimpleName();
    private LookController lookController = new LookController();

    protected void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_LOOKS_PREFERENCE_KEY;
        this.hasDetails = true;
        this.adapter = new LookAdapter(BackpackListManager.getInstance().getBackpackedLooks());
        onAdapterReady();
    }

    protected void unpackItems(List<LookData> selectedItems) {
        setShowProgressBar(true);
        Sprite dstSprite = ProjectManager.getInstance().getCurrentSprite();
        int unpackedItemCnt = 0;
        for (LookData item : selectedItems) {
            try {
                dstSprite.getLookList().add(this.lookController.unpack(item, ProjectManager.getInstance().getCurrentlyEditedScene(), dstSprite));
                unpackedItemCnt++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (unpackedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_looks, unpackedItemCnt, new Object[]{Integer.valueOf(unpackedItemCnt)}));
            getActivity().finish();
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
        BackpackListManager.getInstance().saveBackpack();
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            getActivity().finish();
        }
    }

    protected String getItemName(LookData item) {
        return item.getName();
    }

    @PluralsRes
    protected int getActionModeTitleId(int actionModeType) {
        switch (actionModeType) {
            case 1:
                return R.plurals.am_unpack_looks_title;
            case 2:
                return R.plurals.am_delete_looks_title;
            default:
                throw new IllegalStateException("ActionModeType not set correctly");
        }
    }
}
