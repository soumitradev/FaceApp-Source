package org.catrobat.catroid.ui;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.parrot.arsdk.armedia.ARMediaManager;
import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.drone.ardrone.DroneServiceWrapper;
import org.catrobat.catroid.drone.ardrone.DroneStageActivity;
import org.catrobat.catroid.facedetection.FaceDetectionHandler;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.stage.PreStageActivity;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.dialogs.LegoSensorConfigInfoDialog;
import org.catrobat.catroid.ui.recyclerview.activity.ProjectUploadActivity;
import org.catrobat.catroid.ui.recyclerview.controller.SceneController;
import org.catrobat.catroid.ui.recyclerview.dialog.PlaySceneDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.NewItemTextWatcher;
import org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.SceneListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class ProjectActivity extends BaseCastActivity {
    public static final String EXTRA_FRAGMENT_POSITION = "fragmentPosition";
    public static final int FRAGMENT_SCENES = 0;
    public static final int FRAGMENT_SPRITES = 1;
    public static final int SPRITE_CAMERA = 3;
    public static final int SPRITE_FILE = 2;
    public static final int SPRITE_LIBRARY = 1;
    public static final int SPRITE_POCKET_PAINT = 0;
    public static final String TAG = ProjectActivity.class.getSimpleName();

    /* renamed from: org.catrobat.catroid.ui.ProjectActivity$4 */
    class C19034 implements OnClickListener {
        C19034() {
        }

        public void onClick(DialogInterface dialog, int which) {
            ProjectActivity.this.startPreStageActivity();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isFinishing()) {
            SettingsFragment.setToChosenLanguage(this);
            setContentView(R.layout.activity_recycler);
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            int fragmentPosition = 0;
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                fragmentPosition = bundle.getInt("fragmentPosition", 0);
            }
            loadFragment(fragmentPosition);
            showLegoSensorConfigInfo();
        }
    }

    private void loadFragment(int fragmentPosition) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (fragmentPosition) {
            case 0:
                fragmentTransaction.replace(R.id.fragment_container, new SceneListFragment(), SceneListFragment.TAG);
                break;
            case 1:
                fragmentTransaction.replace(R.id.fragment_container, new SpriteListFragment(), SpriteListFragment.TAG);
                break;
            default:
                throw new IllegalArgumentException("Invalid fragmentPosition in Activity.");
        }
        fragmentTransaction.commit();
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.new_scene) {
            handleAddSceneButton();
        } else if (itemId != R.id.upload) {
            return super.onOptionsItemSelected(item);
        } else {
            Project currentProject = ProjectManager.getInstance().getCurrentProject();
            Intent intent = new Intent(this, ProjectUploadActivity.class);
            intent.putExtra("projectName", currentProject.getName());
            startActivity(intent);
        }
        return true;
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        boolean z = true;
        if (ProjectManager.getInstance().getCurrentProject().getSceneList().size() <= 1) {
            z = false;
        }
        boolean multiSceneProject = z;
        if ((getCurrentFragment() instanceof SpriteListFragment) && multiSceneProject) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SceneListFragment(), SceneListFragment.TAG).commit();
        } else {
            super.onBackPressed();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == StageActivity.STAGE_ACTIVITY_FINISH) {
            SensorHandler.stopSensorListeners();
            FaceDetectionHandler.stopFaceDetection();
        }
        if (resultCode != -1) {
            if (SettingsFragment.isCastSharedPreferenceEnabled(this) && ProjectManager.getInstance().getCurrentProject().isCastProject() && !CastManager.getInstance().isConnected()) {
                CastManager.getInstance().openDeviceSelectorOrDisconnectDialog(this);
            }
            return;
        }
        if (requestCode != 101) {
            switch (requestCode) {
                case 0:
                    addSpriteFromUri(Uri.fromFile(new File(data.getStringExtra(Constants.EXTRA_PICTURE_PATH_POCKET_PAINT))));
                    break;
                case 1:
                    addSpriteFromUri(Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))));
                    break;
                case 2:
                    addSpriteFromUri(data.getData());
                    break;
                case 3:
                    File file = FlavoredConstants.DEFAULT_ROOT_DIRECTORY;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(getString(R.string.default_look_name));
                    stringBuilder.append(ARMediaManager.ARMEDIA_MANAGER_JPG);
                    addSpriteFromUri(Uri.fromFile(new File(file, stringBuilder.toString())));
                    break;
                default:
                    break;
            }
        }
        startStageActivity();
    }

    public void addSpriteFromUri(final Uri uri) {
        final Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        String name = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (name == null) {
            name = getString(R.string.default_look_name);
        } else {
            name = StorageOperations.getSanitizedFileName(name);
        }
        name = new UniqueNameProvider().getUniqueNameInNameables(name, currentScene.getSpriteList());
        final String lookName = name;
        Builder builder = new Builder(this);
        builder.setHint(getString(R.string.sprite_name_label)).setText(name).setTextWatcher(new NewItemTextWatcher(currentScene.getSpriteList())).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() {
            public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
                File imageDirectory = new File(currentScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
                Sprite sprite = new Sprite(textInput);
                currentScene.getSpriteList().add(sprite);
                try {
                    sprite.getLookList().add(new LookData(lookName, StorageOperations.copyUriToDir(ProjectActivity.this.getContentResolver(), uri, imageDirectory, lookName)));
                } catch (IOException e) {
                    Log.e(ProjectActivity.TAG, Log.getStackTraceString(e));
                }
                if (ProjectActivity.this.getCurrentFragment() instanceof SpriteListFragment) {
                    ((SpriteListFragment) ProjectActivity.this.getCurrentFragment()).notifyDataSetChanged();
                }
            }
        });
        builder.setTitle(R.string.new_sprite_dialog_title).setNegativeButton(R.string.cancel, null).create().show();
    }

    public void handleAddButton(View view) {
        if (getCurrentFragment() instanceof SceneListFragment) {
            handleAddSceneButton();
            return;
        }
        if (getCurrentFragment() instanceof SpriteListFragment) {
            handleAddSpriteButton();
        }
    }

    public void handleAddSceneButton() {
        final Project currentProject = ProjectManager.getInstance().getCurrentProject();
        String defaultSceneName = SceneController.getUniqueDefaultSceneName(getResources(), currentProject.getSceneList());
        Builder builder = new Builder(this);
        builder.setHint(getString(R.string.scene_name_label)).setText(defaultSceneName).setTextWatcher(new NewItemTextWatcher(currentProject.getSceneList())).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() {
            public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
                currentProject.addScene(SceneController.newSceneWithBackgroundSprite(textInput, ProjectActivity.this.getString(R.string.background), currentProject));
                if (ProjectActivity.this.getCurrentFragment() instanceof SceneListFragment) {
                    ((RecyclerViewFragment) ProjectActivity.this.getCurrentFragment()).notifyDataSetChanged();
                    return;
                }
                Intent intent = new Intent(ProjectActivity.this, ProjectActivity.class);
                intent.putExtra("fragmentPosition", 0);
                ProjectActivity.this.startActivity(intent);
                ProjectActivity.this.finish();
            }
        });
        builder.setTitle(R.string.new_scene_dialog).setNegativeButton(R.string.cancel, null).create().show();
    }

    public void handleAddSpriteButton() {
        View view = View.inflate(this, R.layout.dialog_new_look, null);
        final AlertDialog alertDialog = new AlertDialog$Builder(this).setTitle(R.string.new_sprite_dialog_title).setView(view).create();
        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.dialog_new_look_camera:
                        File file = FlavoredConstants.DEFAULT_ROOT_DIRECTORY;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(ProjectActivity.this.getString(R.string.default_look_name));
                        stringBuilder.append(ARMediaManager.ARMEDIA_MANAGER_JPG);
                        new ImportFromCameraLauncher(ProjectActivity.this, Uri.fromFile(new File(file, stringBuilder.toString()))).startActivityForResult(3);
                        break;
                    case R.id.dialog_new_look_gallery:
                        new ImportFromFileLauncher(ProjectActivity.this, "image/*", ProjectActivity.this.getString(R.string.select_look_from_gallery)).startActivityForResult(2);
                        break;
                    case R.id.dialog_new_look_media_library:
                        new ImportFormMediaLibraryLauncher(ProjectActivity.this, FlavoredConstants.LIBRARY_LOOKS_URL).startActivityForResult(1);
                        break;
                    case R.id.dialog_new_look_paintroid:
                        new ImportFromPocketPaintLauncher(ProjectActivity.this).startActivityForResult(0);
                        break;
                    default:
                        break;
                }
                alertDialog.dismiss();
            }
        };
        view.findViewById(R.id.dialog_new_look_paintroid).setOnClickListener(onClickListener);
        view.findViewById(R.id.dialog_new_look_media_library).setOnClickListener(onClickListener);
        view.findViewById(R.id.dialog_new_look_gallery).setOnClickListener(onClickListener);
        view.findViewById(R.id.dialog_new_look_camera).setOnClickListener(onClickListener);
        alertDialog.show();
    }

    public void handlePlayButton(View view) {
        ProjectManager projectManager = ProjectManager.getInstance();
        Scene currentScene = projectManager.getCurrentlyEditedScene();
        Scene defaultScene = projectManager.getCurrentProject().getDefaultScene();
        if (currentScene.getName().equals(defaultScene.getName())) {
            projectManager.setCurrentlyPlayingScene(defaultScene);
            projectManager.setStartScene(defaultScene);
            startPreStageActivity();
            return;
        }
        new PlaySceneDialog.Builder(this).setPositiveButton(R.string.play, new C19034()).create().show();
    }

    void startPreStageActivity() {
        startActivityForResult(new Intent(this, PreStageActivity.class), 101);
    }

    void startStageActivity() {
        if (DroneServiceWrapper.checkARDroneAvailability()) {
            startActivity(new Intent(this, DroneStageActivity.class));
        } else {
            startActivity(new Intent(this, StageActivity.class));
        }
    }

    private void showLegoSensorConfigInfo() {
        if (ProjectManager.getInstance().getCurrentProject() != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            boolean nxtDialogDisabled = preferences.getBoolean(SettingsFragment.SETTINGS_MINDSTORMS_NXT_SHOW_SENSOR_INFO_BOX_DISABLED, false);
            boolean ev3DialogDisabled = preferences.getBoolean(SettingsFragment.SETTINGS_MINDSTORMS_EV3_SHOW_SENSOR_INFO_BOX_DISABLED, false);
            ResourcesSet resourcesSet = ProjectManager.getInstance().getCurrentProject().getRequiredResources();
            if (!nxtDialogDisabled && resourcesSet.contains(Integer.valueOf(2))) {
                LegoSensorConfigInfoDialog.newInstance(0).show(getSupportFragmentManager(), LegoSensorConfigInfoDialog.DIALOG_FRAGMENT_TAG);
            }
            if (!ev3DialogDisabled && resourcesSet.contains(Integer.valueOf(20))) {
                LegoSensorConfigInfoDialog.newInstance(1).show(getSupportFragmentManager(), LegoSensorConfigInfoDialog.DIALOG_FRAGMENT_TAG);
            }
        }
    }
}
