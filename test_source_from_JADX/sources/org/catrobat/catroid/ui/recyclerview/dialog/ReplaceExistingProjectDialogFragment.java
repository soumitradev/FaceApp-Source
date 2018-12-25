package org.catrobat.catroid.ui.recyclerview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.TextWatcher;
import org.catrobat.catroid.utils.DownloadUtil;
import org.catrobat.catroid.utils.Utils;

public class ReplaceExistingProjectDialogFragment extends DialogFragment {
    public static final String BUNDLE_KEY_PROGRAM_NAME = "programName";
    public static final String BUNDLE_KEY_URL = "url";
    public static final String TAG = ReplaceExistingProjectDialogFragment.class.getSimpleName();

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment$1 */
    class C21241 extends TextWatcher {
        C21241() {
        }

        @Nullable
        public String validateInput(String input, Context context) {
            String error = null;
            if (input.isEmpty()) {
                return context.getString(R.string.name_empty);
            }
            input = input.trim();
            if (input.isEmpty()) {
                error = context.getString(R.string.name_consists_of_spaces_only);
            } else if (Utils.checkIfProjectExistsOrIsDownloadingIgnoreCase(input)) {
                error = context.getString(R.string.name_already_exists);
            }
            return error;
        }
    }

    public static ReplaceExistingProjectDialogFragment newInstance(String programName, String url) {
        ReplaceExistingProjectDialogFragment dialog = new ReplaceExistingProjectDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("programName", programName);
        bundle.putString("url", url);
        dialog.setArguments(bundle);
        return dialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String programName = getArguments().getString("programName");
        final String url = getArguments().getString("url");
        View view = View.inflate(getActivity(), R.layout.dialog_overwrite_project, null);
        final TextInputLayout inputLayout = (TextInputLayout) view.findViewById(R.id.input);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        final TextWatcher textWatcher = new C21241();
        final AlertDialog alertDialog = new Builder(getContext()).setText(programName).setTextWatcher(textWatcher).setPositiveButton(getString(R.string.ok), new OnClickListener() {
            public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rename:
                        DownloadUtil.getInstance().startDownload(ReplaceExistingProjectDialogFragment.this.getActivity(), url, textInput, true);
                        return;
                    case R.id.replace:
                        ProjectManager.getInstance().setProject(null);
                        DownloadUtil.getInstance().startDownload(ReplaceExistingProjectDialogFragment.this.getActivity(), url, textInput, false);
                        return;
                    default:
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(ReplaceExistingProjectDialogFragment.TAG);
                        stringBuilder.append(": Cannot find RadioButton.");
                        throw new IllegalStateException(stringBuilder.toString());
                }
            }
        }).setTitle(R.string.overwrite_title).setView(view).setNegativeButton(R.string.notification_download_project_cancel, null).create();
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                boolean z = true;
                switch (checkedId) {
                    case R.id.rename:
                        inputLayout.setVisibility(0);
                        Button button = alertDialog.getButton(-1);
                        if (textWatcher.validateInput(inputLayout.getEditText().toString(), ReplaceExistingProjectDialogFragment.this.getContext()) != null) {
                            z = false;
                        }
                        button.setEnabled(z);
                        return;
                    case R.id.replace:
                        inputLayout.setVisibility(8);
                        alertDialog.getButton(-1).setEnabled(true);
                        return;
                    default:
                        return;
                }
            }
        });
        return alertDialog;
    }
}
