package org.catrobat.catroid.ui.recyclerview.fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.PluralsRes;
import android.util.Log;
import android.view.Menu;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.nfc.NfcHandler;
import org.catrobat.catroid.ui.recyclerview.adapter.NfcTagAdapter;
import org.catrobat.catroid.utils.ToastUtil;

public class NfcTagListFragment extends RecyclerViewFragment<NfcTagData> {
    public static final String TAG = NfcTagListFragment.class.getSimpleName();
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        this.pendingIntent = PendingIntent.getActivity(getActivity(), 0, new Intent(getActivity(), getActivity().getClass()).addFlags(536870912), 0);
        if (this.nfcAdapter != null && !this.nfcAdapter.isEnabled()) {
            ToastUtil.showError(getActivity(), (int) R.string.nfc_not_activated);
            startActivity(new Intent("android.settings.NFC_SETTINGS"));
        } else if (this.nfcAdapter == null) {
            ToastUtil.showError(getActivity(), (int) R.string.no_nfc_available);
        }
    }

    public void onResume() {
        super.onResume();
        if (this.nfcAdapter != null) {
            this.nfcAdapter.enableForegroundDispatch(getActivity(), this.pendingIntent, null, (String[][]) null);
        }
    }

    public void onPause() {
        super.onPause();
        if (this.nfcAdapter != null) {
            this.nfcAdapter.disableForegroundDispatch(getActivity());
        }
    }

    protected void initializeAdapter() {
        List<NfcTagData> items = ProjectManager.getInstance().getCurrentSprite().getNfcTagList();
        this.sharedPreferenceDetailsKey = "showDetailsNfcTags";
        this.adapter = new NfcTagAdapter(items);
        this.emptyView.setText(R.string.fragment_nfctag_text_description);
        onAdapterReady();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.backpack).setVisible(false);
    }

    public void onNewIntent(Intent intent) {
        String uid = NfcHandler.getTagIdFromIntent(intent);
        NfcHandler.setLastNfcTagId(uid);
        NfcHandler.setLastNfcTagMessage(NfcHandler.getMessageFromIntent(intent));
        if (uid != null) {
            NfcTagData item = new NfcTagData();
            item.setName(this.uniqueNameProvider.getUniqueNameInNameables(getString(R.string.default_tag_name), this.adapter.getItems()));
            item.setNfcTagUid(uid);
            this.adapter.add(item);
            return;
        }
        Log.e(TAG, "NFC Tag does not have a UID.");
    }

    protected void packItems(List<NfcTagData> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TAG);
        stringBuilder.append(": NfcTags cannot be backpacked.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    protected boolean isBackpackEmpty() {
        return true;
    }

    protected void switchToBackpack() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TAG);
        stringBuilder.append(": NfcTags cannot be backpacked.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    protected void copyItems(List<NfcTagData> selectedItems) {
        setShowProgressBar(true);
        for (NfcTagData item : selectedItems) {
            String name = this.uniqueNameProvider.getUniqueNameInNameables(item.getName(), this.adapter.getItems());
            NfcTagData newItem = new NfcTagData();
            newItem.setName(name);
            newItem.setNfcTagUid(item.getNfcTagUid());
            this.adapter.add(newItem);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.copied_nfc_tags, selectedItems.size(), new Object[]{Integer.valueOf(selectedItems.size())}));
        finishActionMode();
    }

    @PluralsRes
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_nfc_tags;
    }

    protected void deleteItems(List<NfcTagData> selectedItems) {
        setShowEmptyView(true);
        for (NfcTagData item : selectedItems) {
            this.adapter.remove(item);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_nfc_tags, selectedItems.size(), new Object[]{Integer.valueOf(selectedItems.size())}));
        finishActionMode();
    }

    protected int getRenameDialogTitle() {
        return R.string.rename_nfctag_dialog;
    }

    protected int getRenameDialogHint() {
        return R.string.nfc_tag_name_label;
    }

    @PluralsRes
    protected int getActionModeTitleId(int actionModeType) {
        switch (actionModeType) {
            case 2:
                return R.plurals.am_copy_nfc_tags_title;
            case 3:
                return R.plurals.am_delete_nfc_tags_title;
            case 4:
                return R.plurals.am_rename_nfc_tags_title;
            default:
                throw new IllegalStateException("ActionModeType not set correctly");
        }
    }

    public void onItemClick(NfcTagData item) {
    }
}
