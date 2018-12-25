package org.catrobat.catroid.ui.recyclerview.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.transfers.ProjectUploadService;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.StatusBarNotificationManager;
import org.catrobat.catroid.web.ProgressResponseBody;
import org.catrobat.catroid.web.ServerCalls;

public class UploadProgressDialogFragment extends DialogFragment {
    public static final String NUMBER_OF_UPLOADED_PROJECTS = "number_of_uploaded_projects";
    public static final String TAG = UploadProgressDialogFragment.class.getSimpleName();
    private Handler handler = new Handler();
    private String openAuthProvider = Constants.NO_OAUTH_PROVIDER;
    private ProgressBar progressBar;
    AlertDialog progressBarDialog;
    private int progressPercent;
    private ImageView successImage;

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.UploadProgressDialogFragment$2 */
    class C19812 implements OnClickListener {
        C19812() {
        }

        public void onClick(View v) {
            UploadProgressDialogFragment uploadProgressDialogFragment = UploadProgressDialogFragment.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Constants.SHARE_PROGRAM_URL);
            stringBuilder.append(ServerCalls.getInstance().getProjectId());
            uploadProgressDialogFragment.startWebViewActivity(stringBuilder.toString());
            UploadProgressDialogFragment.this.dismiss();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.UploadProgressDialogFragment$3 */
    class C19823 implements OnClickListener {
        C19823() {
        }

        public void onClick(View v) {
            UploadProgressDialogFragment.this.dismiss();
        }
    }

    @SuppressLint({"ParcelCreator"})
    private class UploadReceiver extends ResultReceiver {
        UploadReceiver(Handler handler) {
            super(handler);
        }

        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 100) {
                long progress = resultData.getLong("currentUploadProgress");
                boolean endOfFileReached = resultData.getBoolean(ProgressResponseBody.TAG_ENDOFFILE);
                int notificationId = resultData.getInt("notificationId");
                String projectName = resultData.getString("projectName");
                if (endOfFileReached) {
                    UploadProgressDialogFragment.this.progressPercent = 100;
                } else {
                    UploadProgressDialogFragment.this.progressPercent = (int) FileMetaDataExtractor.getProgressFromBytes(projectName, Long.valueOf(progress));
                }
                StatusBarNotificationManager.getInstance().showOrUpdateNotification(notificationId, UploadProgressDialogFragment.this.progressPercent);
            }
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        if (bundle != null) {
            this.openAuthProvider = bundle.getString(Constants.CURRENT_OAUTH_PROVIDER);
        }
        View view = View.inflate(getActivity(), R.layout.dialog_upload_project_progress, null);
        this.progressBar = (ProgressBar) view.findViewById(R.id.dialog_upload_progress_progressbar);
        this.successImage = (ImageView) view.findViewById(R.id.dialog_upload_progress_success_image);
        this.progressBarDialog = new AlertDialog$Builder(getActivity()).setTitle(getString(R.string.upload_project_dialog_title)).setView(view).setPositiveButton(R.string.progress_upload_dialog_show_program, null).setNegativeButton(R.string.cancel, null).setCancelable(false).create();
        return this.progressBarDialog;
    }

    public void onStart() {
        super.onStart();
        final AlertDialog alertDialog = (AlertDialog) getDialog();
        alertDialog.getButton(-2).setText(getString(R.string.done));
        alertDialog.getButton(-1).setEnabled(false);
        uploadProject(getArguments().getString(Constants.PROJECT_UPLOAD_NAME), getArguments().getString(Constants.PROJECT_UPLOAD_DESCRIPTION));
        new Thread(new Runnable() {

            /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.UploadProgressDialogFragment$1$1 */
            class C19791 implements Runnable {
                C19791() {
                }

                public void run() {
                    if (UploadProgressDialogFragment.this.progressPercent == 100) {
                        alertDialog.getButton(-1).setEnabled(true);
                        UploadProgressDialogFragment.this.progressBar.setVisibility(8);
                        UploadProgressDialogFragment.this.successImage.setImageResource(R.drawable.ic_upload_success);
                        UploadProgressDialogFragment.this.successImage.setVisibility(0);
                    }
                }
            }

            public void run() {
                while (UploadProgressDialogFragment.this.progressPercent != 100) {
                    UploadProgressDialogFragment.this.progressPercent = StatusBarNotificationManager.getInstance().getProgressPercent();
                    UploadProgressDialogFragment.this.handler.post(new C19791());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d(UploadProgressDialogFragment.TAG, Log.getStackTraceString(e));
                    }
                }
            }
        }).start();
        alertDialog.getButton(-1).setOnClickListener(new C19812());
        alertDialog.getButton(-2).setOnClickListener(new C19823());
    }

    public void startWebViewActivity(String url) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private void uploadProject(String uploadName, String projectDescription) {
        ProjectManager projectManager = ProjectManager.getInstance();
        String projectPath = PathBuilder.buildProjectPath(projectManager.getCurrentProject().getName());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String token = sharedPreferences.getString(Constants.TOKEN, Constants.NO_TOKEN);
        String username = sharedPreferences.getString(Constants.USERNAME, Constants.NO_USERNAME);
        Intent intent = new Intent(getActivity(), ProjectUploadService.class);
        List<String> sceneNameList = projectManager.getCurrentProject().getSceneNames();
        String[] sceneNames = new String[sceneNameList.size()];
        sceneNameList.toArray(sceneNames);
        intent.putExtra("receiver", new UploadReceiver(new Handler()));
        intent.putExtra("uploadName", uploadName);
        intent.putExtra("projectDescription", projectDescription);
        intent.putExtra("projectPath", projectPath);
        intent.putExtra(Constants.USERNAME, username);
        intent.putExtra(Constants.TOKEN, token);
        intent.putExtra("provider", this.openAuthProvider);
        intent.putExtra("sceneNames", sceneNames);
        intent.putExtra("notificationId", StatusBarNotificationManager.getInstance().createUploadNotification(getActivity(), uploadName));
        getActivity().startService(intent);
        int numberOfUploadedProjects = sharedPreferences.getInt(NUMBER_OF_UPLOADED_PROJECTS, 0) + 1;
        if (numberOfUploadedProjects == 2) {
            new RatePocketCodeDialogFragment().show(getFragmentManager(), RatePocketCodeDialogFragment.TAG);
        }
        sharedPreferences.edit().putInt(NUMBER_OF_UPLOADED_PROJECTS, numberOfUploadedProjects).commit();
    }
}
