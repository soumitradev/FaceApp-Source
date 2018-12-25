package org.catrobat.catroid.ui.recyclerview.backpack;

import android.support.annotation.PluralsRes;
import android.util.Log;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.ScriptAdapter;
import org.catrobat.catroid.ui.recyclerview.controller.ScriptController;
import org.catrobat.catroid.utils.ToastUtil;

public class BackpackScriptFragment extends BackpackRecyclerViewFragment<String> {
    public static final String TAG = BackpackScriptFragment.class.getSimpleName();
    private ScriptController scriptController = new ScriptController();

    protected void initializeAdapter() {
        this.adapter = new ScriptAdapter(BackpackListManager.getInstance().getBackpackedScriptGroups());
        onAdapterReady();
    }

    protected void unpackItems(List<String> selectedItems) {
        setShowProgressBar(true);
        int unpackedItemCnt = 0;
        for (String item : selectedItems) {
            for (Script script : (List) BackpackListManager.getInstance().getBackpackedScripts().get(item)) {
                try {
                    this.scriptController.unpack(script, ProjectManager.getInstance().getCurrentSprite());
                    unpackedItemCnt++;
                } catch (CloneNotSupportedException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }
        if (unpackedItemCnt > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_scripts, unpackedItemCnt, new Object[]{Integer.valueOf(unpackedItemCnt)}));
            getActivity().finish();
        }
        finishActionMode();
    }

    @PluralsRes
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_scripts;
    }

    protected void deleteItems(List<String> selectedItems) {
        setShowProgressBar(true);
        for (String item : selectedItems) {
            BackpackListManager.getInstance().removeItemFromScriptBackPack(item);
            this.adapter.remove(item);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_scripts, selectedItems.size(), new Object[]{Integer.valueOf(selectedItems.size())}));
        BackpackListManager.getInstance().saveBackpack();
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            getActivity().finish();
        }
    }

    protected String getItemName(String item) {
        return item;
    }

    @PluralsRes
    protected int getActionModeTitleId(int actionModeType) {
        switch (actionModeType) {
            case 1:
                return R.plurals.am_unpack_scripts_title;
            case 2:
                return R.plurals.am_delete_scripts_title;
            default:
                throw new IllegalStateException("ActionModeType not set correctly");
        }
    }
}
