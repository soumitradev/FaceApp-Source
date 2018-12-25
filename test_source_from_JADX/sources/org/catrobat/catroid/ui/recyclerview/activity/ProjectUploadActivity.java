package org.catrobat.catroid.ui.recyclerview.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.transfers.CheckTokenTask;
import org.catrobat.catroid.transfers.CheckTokenTask.TokenCheckListener;
import org.catrobat.catroid.transfers.GetTagsTask;
import org.catrobat.catroid.transfers.GetTagsTask.TagResponseListener;
import org.catrobat.catroid.ui.BaseActivity;
import org.catrobat.catroid.ui.SignInActivity;
import org.catrobat.catroid.ui.dialogs.SelectTagsDialogFragment;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectLoaderTask;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectLoaderTask.ProjectLoaderListener;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ServerCalls;

public class ProjectUploadActivity extends BaseActivity implements ProjectLoaderListener, TokenCheckListener, TagResponseListener {
    public static final String PROJECT_NAME = "projectName";
    public static final int SIGN_IN_CODE = 42;
    public static final String TAG = ProjectUploadActivity.class.getSimpleName();
    private TextInputLayout descriptionInputLayout;
    private TextInputLayout nameInputLayout;
    private List<String> tags = new ArrayList();

    private class TextChangedListener implements TextWatcher {
        private TextChangedListener() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ProjectUploadActivity.this.nameInputLayout.setError(null);
            ProjectUploadActivity.this.invalidateOptionsMenu();
        }

        public void afterTextChanged(Editable s) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.upload_project_dialog_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setShowProgressBar(true);
        if (getIntent().getExtras() != null) {
            new ProjectLoaderTask(this, this).execute(new String[]{bundle.getString("projectName")});
            return;
        }
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_upload, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean enableNextButton = (this.nameInputLayout == null || this.nameInputLayout.getEditText().getText().toString().isEmpty()) ? false : true;
        menu.findItem(R.id.next).setEnabled(enableNextButton);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.next) {
            return super.onOptionsItemSelected(item);
        }
        onNextButtonClick();
        return true;
    }

    public void onLoadFinished(boolean success, String message) {
        if (success) {
            getTags();
            verifyUserIdentity();
            return;
        }
        ToastUtil.showError((Context) this, message);
        finish();
    }

    private void verifyUserIdentity() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sharedPreferences.getString(Constants.TOKEN, Constants.NO_TOKEN);
        String username = sharedPreferences.getString(Constants.USERNAME, Constants.NO_USERNAME);
        boolean isTokenSetInPreferences = (token.equals(Constants.NO_TOKEN) || token.length() != 32 || token.equals(ServerCalls.TOKEN_CODE_INVALID)) ? false : true;
        if (isTokenSetInPreferences) {
            new CheckTokenTask(this).execute(new String[]{token, username});
        } else {
            startSignInWorkflow();
        }
    }

    private void onCreateView() {
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        new ProjectAndSceneScreenshotLoader(this).loadAndShowScreenshot(currentProject.getName(), currentProject.getDefaultScene().getName(), false, (ImageView) findViewById(R.id.project_image_view));
        ((TextView) findViewById(R.id.project_size_view)).setText(FileMetaDataExtractor.getSizeAsString(new File(PathBuilder.buildProjectPath(currentProject.getName())), this));
        this.nameInputLayout = (TextInputLayout) findViewById(R.id.input_project_name);
        this.descriptionInputLayout = (TextInputLayout) findViewById(R.id.input_project_description);
        this.nameInputLayout.getEditText().setText(currentProject.getName());
        this.descriptionInputLayout.getEditText().setText(currentProject.getDescription());
        this.nameInputLayout.getEditText().addTextChangedListener(new TextChangedListener());
        invalidateOptionsMenu();
    }

    private void onNextButtonClick() {
        String name = this.nameInputLayout.getEditText().getText().toString().trim();
        String description = this.descriptionInputLayout.getEditText().getText().toString().trim();
        if (name.isEmpty()) {
            this.nameInputLayout.setError(getString(R.string.error_no_program_name_entered));
        } else if (name.equals(getString(R.string.default_project_name))) {
            this.nameInputLayout.setError(getString(R.string.error_upload_project_with_default_name));
        } else {
            ProjectManager projectManager = ProjectManager.getInstance();
            if (!Utils.isDefaultProject(projectManager.getCurrentProject(), this)) {
                if (!name.equals(projectManager.getCurrentProject().getName())) {
                    projectManager.renameProject(name, this);
                }
                projectManager.getCurrentProject().setDescription(description);
                projectManager.getCurrentProject().setDeviceData(this);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.PROJECT_UPLOAD_NAME, name);
                bundle.putString(Constants.PROJECT_UPLOAD_DESCRIPTION, description);
                SelectTagsDialogFragment dialog = new SelectTagsDialogFragment();
                dialog.setTags(this.tags);
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), SelectTagsDialogFragment.TAG);
            }
        }
    }

    public void onTokenCheckComplete(boolean tokenValid, boolean connectionFailed) {
        if (connectionFailed) {
            ToastUtil.showError((Context) this, (int) R.string.error_internet_connection);
            finish();
        } else if (tokenValid) {
            onCreateView();
            setShowProgressBar(false);
        } else {
            startSignInWorkflow();
        }
    }

    public void startSignInWorkflow() {
        startActivityForResult(new Intent(this, SignInActivity.class), 42);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 42) {
            super.onActivityResult(requestCode, resultCode, data);
        } else if (resultCode == -1) {
            onCreateView();
            setShowProgressBar(false);
        } else {
            finish();
        }
    }

    public void setShowProgressBar(boolean show) {
        int i = 8;
        findViewById(R.id.progress_bar).setVisibility(show ? 0 : 8);
        View findViewById = findViewById(R.id.upload_layout);
        if (!show) {
            i = 0;
        }
        findViewById.setVisibility(i);
    }

    private void getTags() {
        GetTagsTask getTagsTask = new GetTagsTask();
        getTagsTask.setOnTagsResponseListener(this);
        getTagsTask.execute(new String[0]);
    }

    public void onTagsReceived(List<String> tags) {
        this.tags = tags;
    }
}
