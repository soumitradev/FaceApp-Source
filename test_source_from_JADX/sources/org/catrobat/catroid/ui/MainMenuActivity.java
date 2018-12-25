package org.catrobat.catroid.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog$Builder;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.BuildConfig;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.ZipArchiver;
import org.catrobat.catroid.stage.PreStageActivity;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.dialogs.TermsOfUseDialogFragment;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectLoaderTask;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectLoaderTask.ProjectLoaderListener;
import org.catrobat.catroid.ui.recyclerview.dialog.AboutDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.PrivacyPolicyDialogFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.ScreenValueHandler;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;

public class MainMenuActivity extends BaseCastActivity implements ProjectLoaderListener {
    private static final int ACCESS_STORAGE = 0;
    protected static final int ERROR = 2;
    protected static final int FRAGMENT = 1;
    protected static final int PROGRESS_BAR = 0;
    public static final String TAG = MainMenuActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsFragment.setToChosenLanguage(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
        ScreenValueHandler.updateScreenWidthAndHeight(this);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(SharedPreferenceKeys.AGREED_TO_PRIVACY_POLICY_PREFERENCE_KEY, false)) {
            loadContent();
        } else {
            setContentView(R.layout.privacy_policy_view);
        }
    }

    public void handleAgreedToPrivacyPolicyButton(View view) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(SharedPreferenceKeys.AGREED_TO_PRIVACY_POLICY_PREFERENCE_KEY, true).commit();
        loadContent();
    }

    public void handleDeclinedPrivacyPolicyButton(View view) {
        View dialogView = View.inflate(this, R.layout.declined_privacy_agreement_alert_view, null);
        ((TextView) dialogView.findViewById(R.id.share_website_view)).setText(Html.fromHtml(getString(R.string.about_link_template, new Object[]{Constants.CATROBAT_ABOUT_URL, getString(R.string.share_website_text)})));
        new AlertDialog$Builder(this).setView(dialogView).setNeutralButton(R.string.ok, null).create().show();
    }

    private void loadContent() {
        setContentView(R.layout.activity_main_menu);
        showContentView(0);
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            onPermissionsGranted();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
        }
    }

    private void showContentView(int content) {
        View progressBar = findViewById(R.id.progress_bar);
        View fragment = findViewById(R.id.fragment_container);
        View errorView = findViewById(R.id.runtime_permission_error_view);
        switch (content) {
            case 0:
                fragment.setVisibility(8);
                errorView.setVisibility(8);
                progressBar.setVisibility(0);
                return;
            case 1:
                fragment.setVisibility(0);
                errorView.setVisibility(8);
                progressBar.setVisibility(8);
                return;
            case 2:
                fragment.setVisibility(8);
                errorView.setVisibility(0);
                progressBar.setVisibility(8);
                return;
            default:
                return;
        }
    }

    private void onPermissionsGranted() {
        setContentView(R.layout.activity_main_menu_splashscreen);
        prepareStandaloneProject();
    }

    private void onPermissionDenied(int requestCode) {
        if (requestCode == 0) {
            ((TextView) findViewById(R.id.runtime_permission_error_view)).setText(R.string.error_no_write_access);
            showContentView(2);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                onPermissionDenied(requestCode);
            } else {
                onPermissionsGranted();
            }
        }
    }

    public void onPause() {
        super.onPause();
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        if (currentProject != null) {
            ProjectManager.getInstance().saveProject(getApplicationContext());
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString("projectName", currentProject.getName()).commit();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        String scratchConverter = getString(R.string.main_menu_scratch_converter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(scratchConverter);
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(getString(R.string.beta));
        SpannableString scratchConverterBeta = new SpannableString(stringBuilder.toString());
        scratchConverterBeta.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.beta_label_color)), scratchConverter.length(), scratchConverterBeta.length(), 33);
        menu.findItem(R.id.menu_scratch_converter).setTitle(scratchConverterBeta);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_login).setVisible(Utils.isUserLoggedIn(this) ^ true);
        menu.findItem(R.id.menu_logout).setVisible(Utils.isUserLoggedIn(this));
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId != R.id.settings) {
            switch (itemId) {
                case R.id.menu_about:
                    new AboutDialogFragment().show(getSupportFragmentManager(), AboutDialogFragment.TAG);
                    break;
                case R.id.menu_login:
                    startActivity(new Intent(this, SignInActivity.class));
                    break;
                case R.id.menu_logout:
                    Utils.logoutUser(this);
                    ToastUtil.showSuccess((Context) this, (int) R.string.logout_successful);
                    break;
                case R.id.menu_privacy_policy:
                    new PrivacyPolicyDialogFragment().show(getSupportFragmentManager(), PrivacyPolicyDialogFragment.TAG);
                    break;
                case R.id.menu_rate_app:
                    if (!Utils.isNetworkAvailable(this)) {
                        ToastUtil.showError((Context) this, (int) R.string.error_internet_connection);
                        break;
                    }
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("market://details?id=");
                        stringBuilder.append(getPackageName());
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
                    } catch (ActivityNotFoundException e) {
                        ToastUtil.showError((Context) this, (int) R.string.main_menu_play_store_not_installed);
                    }
                    break;
                default:
                    switch (itemId) {
                        case R.id.menu_scratch_converter:
                            if (!Utils.isNetworkAvailable(this)) {
                                ToastUtil.showError((Context) this, (int) R.string.error_internet_connection);
                                break;
                            }
                            startActivity(new Intent(this, ScratchConverterActivity.class));
                            break;
                        case R.id.menu_terms_of_use:
                            new TermsOfUseDialogFragment().show(getSupportFragmentManager(), TermsOfUseDialogFragment.TAG);
                            break;
                        default:
                            return super.onOptionsItemSelected(item);
                    }
            }
        }
        startActivity(new Intent(this, SettingsActivity.class));
        return true;
    }

    private void prepareStandaloneProject() {
        try {
            new ZipArchiver().unzip(getAssets().open("generated70026.zip"), new File(PathBuilder.buildProjectPath(BuildConfig.PROJECT_NAME)));
            new ProjectLoaderTask(this, this).execute(new String[]{BuildConfig.PROJECT_NAME});
        } catch (IOException e) {
            Log.e("STANDALONE", "Could not unpack Standalone Program: ", e);
        }
    }

    public void onLoadFinished(boolean success, String message) {
        startActivityForResult(new Intent(this, PreStageActivity.class), 101);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == -1) {
            SensorHandler.startSensorListener(this);
            Intent intent = new Intent(this, StageActivity.class);
            intent.addFlags(67108864);
            intent.addFlags(268435456);
            intent.addFlags(32768);
            startActivityForResult(intent, StageActivity.STAGE_ACTIVITY_FINISH);
        }
        if (requestCode == StageActivity.STAGE_ACTIVITY_FINISH) {
            finish();
        }
    }
}
