package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.dialog.UploadProgressDialogFragment;
import org.catrobat.catroid.utils.Utils;

public class SelectTagsDialogFragment extends DialogFragment {
    public static final int MAX_NUMBER_OF_TAGS_CHECKED = 3;
    public static final String TAG = SelectTagsDialogFragment.class.getSimpleName();
    public List<String> tags = new ArrayList();

    /* renamed from: org.catrobat.catroid.ui.dialogs.SelectTagsDialogFragment$1 */
    class C19311 implements OnClickListener {
        C19311() {
        }

        public void onClick(DialogInterface dialog, int id) {
            Utils.invalidateLoginTokenIfUserRestricted(SelectTagsDialogFragment.this.getActivity());
        }
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            dismiss();
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        final List<String> checkedTags = new ArrayList();
        final String[] choiceItems = (String[]) this.tags.toArray(new String[this.tags.size()]);
        return new AlertDialog$Builder(getActivity()).setTitle(R.string.upload_tag_dialog_title).setMultiChoiceItems(choiceItems, null, new OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                if (!isChecked) {
                    checkedTags.remove(choiceItems[indexSelected]);
                } else if (checkedTags.size() >= 3) {
                    ((AlertDialog) dialog).getListView().setItemChecked(indexSelected, false);
                } else {
                    checkedTags.add(choiceItems[indexSelected]);
                }
            }
        }).setPositiveButton(getText(R.string.next), new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ProjectManager.getInstance().getCurrentProject().setTags(checkedTags);
                SelectTagsDialogFragment.this.onPositiveButtonClick();
            }
        }).setNegativeButton(getText(R.string.cancel), new C19311()).setCancelable(false).create();
    }

    private void onPositiveButtonClick() {
        UploadProgressDialogFragment dialog = new UploadProgressDialogFragment();
        dialog.setArguments(getArguments());
        dialog.show(getFragmentManager(), UploadProgressDialogFragment.TAG);
    }
}
