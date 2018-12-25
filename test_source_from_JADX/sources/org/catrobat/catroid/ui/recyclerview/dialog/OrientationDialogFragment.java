package org.catrobat.catroid.ui.recyclerview.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog$Builder;
import android.view.View;
import android.widget.RadioGroup;
import java.io.IOException;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.ToastUtil;

public class OrientationDialogFragment extends DialogFragment {
    public static final String BUNDLE_KEY_CREATE_EMPTY_PROJECT = "createEmptyProject";
    public static final String BUNDLE_KEY_PROJECT_NAME = "projectName";
    public static final String TAG = OrientationDialogFragment.class.getSimpleName();

    public static OrientationDialogFragment newInstance(String projectName, boolean createEmptyProject) {
        OrientationDialogFragment dialog = new OrientationDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("projectName", projectName);
        bundle.putBoolean(BUNDLE_KEY_CREATE_EMPTY_PROJECT, createEmptyProject);
        dialog.setArguments(bundle);
        return dialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.dialog_orientation, null);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        int title = R.string.project_orientation_title;
        if (SettingsFragment.isCastSharedPreferenceEnabled(getActivity())) {
            title = R.string.project_select_screen_title;
            view.findViewById(R.id.cast).setVisibility(0);
        }
        return new AlertDialog$Builder(getContext()).setTitle(title).setView(view).setPositiveButton(R.string.ok, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.cast) {
                    OrientationDialogFragment.this.createProject(false, true);
                } else if (checkedRadioButtonId == R.id.landscape_mode) {
                    OrientationDialogFragment.this.createProject(true, false);
                } else if (checkedRadioButtonId != R.id.portrait) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(OrientationDialogFragment.TAG);
                    stringBuilder.append(": No radio button id match, check layout?");
                    throw new IllegalStateException(stringBuilder.toString());
                } else {
                    OrientationDialogFragment.this.createProject(false, false);
                }
            }
        }).setNegativeButton(R.string.cancel, null).create();
    }

    void createProject(boolean createLandscapeProject, boolean createCastProject) {
        if (getArguments() != null) {
            String projectName = getArguments().getString("projectName");
            Boolean createEmptyProject = Boolean.valueOf(getArguments().getBoolean(BUNDLE_KEY_CREATE_EMPTY_PROJECT));
            if (projectName != null) {
                try {
                    ProjectManager.getInstance().initializeNewProject(projectName, getActivity(), createEmptyProject.booleanValue(), false, createLandscapeProject, createCastProject, false);
                } catch (IOException e) {
                    ToastUtil.showError(getActivity(), (int) R.string.error_new_project);
                }
                getActivity().startActivity(new Intent(getActivity(), ProjectActivity.class));
            }
        }
    }
}
