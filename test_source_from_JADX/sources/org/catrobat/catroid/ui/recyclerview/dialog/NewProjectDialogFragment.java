package org.catrobat.catroid.ui.recyclerview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RadioGroup;
import java.io.IOException;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.drone.ardrone.DroneServiceWrapper;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoServiceWrapper;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.TextWatcher;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;

public class NewProjectDialogFragment extends DialogFragment {
    public static final String TAG = NewProjectDialogFragment.class.getSimpleName();

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.NewProjectDialogFragment$1 */
    class C21221 extends TextWatcher {
        C21221() {
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

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.dialog_new_project, null);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        if (DroneServiceWrapper.isDroneSharedPreferenceEnabled()) {
            view.findViewById(R.id.project_default_drone_radio_button).setVisibility(0);
        }
        if (JumpingSumoServiceWrapper.isJumpingSumoSharedPreferenceEnabled()) {
            view.findViewById(R.id.project_default_jumping_sumo_radio_button).setVisibility(0);
        }
        return new Builder(getContext()).setHint(getString(R.string.project_name_label)).setTextWatcher(new C21221()).setPositiveButton(getString(R.string.ok), new OnClickListener() {
            public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId != R.id.project_empty_radio_button) {
                    switch (checkedRadioButtonId) {
                        case R.id.project_default_drone_radio_button:
                            NewProjectDialogFragment.this.createDroneProject(textInput, false);
                            return;
                        case R.id.project_default_jumping_sumo_radio_button:
                            NewProjectDialogFragment.this.createDroneProject(textInput, true);
                            return;
                        case R.id.project_default_radio_button:
                            NewProjectDialogFragment.this.showOrientationDialog(textInput, false);
                            return;
                        default:
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(NewProjectDialogFragment.TAG);
                            stringBuilder.append(": No radio button id match, check layout?");
                            throw new IllegalStateException(stringBuilder.toString());
                    }
                }
                NewProjectDialogFragment.this.showOrientationDialog(textInput, true);
            }
        }).setTitle(R.string.new_project_dialog_title).setView(view).setNegativeButton(R.string.cancel, null).create();
    }

    void showOrientationDialog(String projectName, boolean createEmptyProject) {
        OrientationDialogFragment.newInstance(projectName, createEmptyProject).show(getFragmentManager(), OrientationDialogFragment.TAG);
    }

    void createDroneProject(String name, boolean jumpingSumo) {
        try {
            ProjectManager.getInstance().initializeNewProject(name, getActivity(), false, true, false, false, jumpingSumo);
            getActivity().startActivity(new Intent(getActivity(), ProjectActivity.class));
        } catch (IOException e) {
            ToastUtil.showError(getActivity(), (int) R.string.error_new_project);
        }
    }
}
