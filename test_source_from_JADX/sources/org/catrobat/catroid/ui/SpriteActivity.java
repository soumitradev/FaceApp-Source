package org.catrobat.catroid.ui;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import com.parrot.arsdk.armedia.ARMediaManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.drone.ardrone.DroneServiceWrapper;
import org.catrobat.catroid.drone.ardrone.DroneStageActivity;
import org.catrobat.catroid.facedetection.FaceDetectionHandler;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.pocketmusic.PocketMusicActivity;
import org.catrobat.catroid.soundrecorder.SoundRecorderActivity;
import org.catrobat.catroid.stage.PreStageActivity;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.fragment.ScriptFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.PlaySceneDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.NewItemTextWatcher;
import org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.LookListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.NfcTagListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.SoundListFragment;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class SpriteActivity extends BaseActivity {
    public static final int BACKGROUND_CAMERA = 7;
    public static final int BACKGROUND_FILE = 6;
    public static final int BACKGROUND_LIBRARY = 5;
    public static final int BACKGROUND_POCKET_PAINT = 4;
    public static final String EXTRA_FRAGMENT_POSITION = "fragmentPosition";
    public static final int FRAGMENT_LOOKS = 1;
    public static final int FRAGMENT_NFC_TAGS = 3;
    public static final int FRAGMENT_SCRIPTS = 0;
    public static final int FRAGMENT_SOUNDS = 2;
    public static final int LOOK_CAMERA = 11;
    public static final int LOOK_FILE = 10;
    public static final int LOOK_LIBRARY = 9;
    public static final int LOOK_POCKET_PAINT = 8;
    public static final int SOUND_FILE = 14;
    public static final int SOUND_LIBRARY = 13;
    public static final int SOUND_RECORD = 12;
    public static final int SPRITE_CAMERA = 3;
    public static final int SPRITE_FILE = 2;
    public static final int SPRITE_LIBRARY = 1;
    public static final int SPRITE_POCKET_PAINT = 0;
    public static final String TAG = SpriteActivity.class.getSimpleName();
    private NewItemInterface<LookData> onNewLookListener;
    private NewItemInterface<SoundInfo> onNewSoundListener;
    private NewItemInterface<Sprite> onNewSpriteListener;

    /* renamed from: org.catrobat.catroid.ui.SpriteActivity$8 */
    class C19118 implements OnClickListener {
        C19118() {
        }

        public void onClick(DialogInterface dialog, int which) {
            SpriteActivity.this.startPreStageActivity();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isFinishing()) {
            SettingsFragment.setToChosenLanguage(this);
            setContentView(R.layout.activity_recycler);
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            updateActionBarTitle();
            int fragmentPosition = 0;
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                fragmentPosition = bundle.getInt("fragmentPosition", 0);
            }
            loadFragment(fragmentPosition);
        }
    }

    private void updateActionBarTitle() {
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        if (currentProject.getSceneList().size() == 1) {
            getSupportActionBar().setTitle(currentSprite.getName());
            return;
        }
        ActionBar supportActionBar = getSupportActionBar();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(currentScene.getName());
        stringBuilder.append(": ");
        stringBuilder.append(currentSprite.getName());
        supportActionBar.setTitle(stringBuilder.toString());
    }

    private void loadFragment(int fragmentPosition) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (fragmentPosition) {
            case 0:
                fragmentTransaction.replace(R.id.fragment_container, new ScriptFragment(), ScriptFragment.TAG);
                break;
            case 1:
                fragmentTransaction.replace(R.id.fragment_container, new LookListFragment(), LookListFragment.TAG);
                break;
            case 2:
                fragmentTransaction.replace(R.id.fragment_container, new SoundListFragment(), SoundListFragment.TAG);
                break;
            case 3:
                fragmentTransaction.replace(R.id.fragment_container, new NfcTagListFragment(), NfcTagListFragment.TAG);
                break;
            default:
                throw new IllegalArgumentException("Invalid fragmentPosition in Activity.");
        }
        fragmentTransaction.commit();
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getCurrentFragment() instanceof NfcTagListFragment) {
            ((NfcTagListFragment) getCurrentFragment()).onNewIntent(intent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_script_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (getCurrentFragment() instanceof ScriptFragment) {
            menu.findItem(R.id.comment_in_out).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void onBackPressed() {
        if (getCurrentFragment() instanceof FormulaEditorFragment) {
            ((FormulaEditorFragment) getCurrentFragment()).promptSave();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
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
            File file;
            StringBuilder stringBuilder;
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
                    file = FlavoredConstants.DEFAULT_ROOT_DIRECTORY;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(getString(R.string.default_look_name));
                    stringBuilder.append(ARMediaManager.ARMEDIA_MANAGER_JPG);
                    addSpriteFromUri(Uri.fromFile(new File(file, stringBuilder.toString())));
                    break;
                case 4:
                    addBackgroundFromUri(Uri.fromFile(new File(data.getStringExtra(Constants.EXTRA_PICTURE_PATH_POCKET_PAINT))));
                    break;
                case 5:
                    addBackgroundFromUri(Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))));
                    break;
                case 6:
                    addBackgroundFromUri(data.getData());
                    break;
                case 7:
                    file = FlavoredConstants.DEFAULT_ROOT_DIRECTORY;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(getString(R.string.default_look_name));
                    stringBuilder.append(ARMediaManager.ARMEDIA_MANAGER_JPG);
                    addBackgroundFromUri(Uri.fromFile(new File(file, stringBuilder.toString())));
                    break;
                case 8:
                    addLookFromUri(Uri.fromFile(new File(data.getStringExtra(Constants.EXTRA_PICTURE_PATH_POCKET_PAINT))));
                    break;
                case 9:
                    addLookFromUri(Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))));
                    break;
                case 10:
                    addLookFromUri(data.getData());
                    break;
                case 11:
                    file = FlavoredConstants.DEFAULT_ROOT_DIRECTORY;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(getString(R.string.default_look_name));
                    stringBuilder.append(ARMediaManager.ARMEDIA_MANAGER_JPG);
                    addLookFromUri(Uri.fromFile(new File(file, stringBuilder.toString())));
                    break;
                case 12:
                case 14:
                    addSoundFromUri(data.getData());
                    break;
                case 13:
                    addSoundFromUri(Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))));
                    break;
                default:
                    break;
            }
        }
        startStageActivity();
    }

    public void registerOnNewSpriteListener(NewItemInterface<Sprite> listener) {
        this.onNewSpriteListener = listener;
    }

    public void unregisterOnNewSpriteListener() {
        this.onNewSpriteListener = null;
    }

    public void registerOnNewLookListener(NewItemInterface<LookData> listener) {
        this.onNewLookListener = listener;
    }

    public void unregisterOnNewLookListener() {
        this.onNewLookListener = null;
    }

    public void registerOnNewSoundListener(NewItemInterface<SoundInfo> listener) {
        this.onNewSoundListener = listener;
    }

    public void unregisterOnNewSoundListener() {
        this.onNewSoundListener = null;
    }

    private void addSpriteFromUri(final Uri uri) {
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
                try {
                    sprite.getLookList().add(new LookData(lookName, StorageOperations.copyUriToDir(SpriteActivity.this.getContentResolver(), uri, imageDirectory, lookName)));
                    currentScene.getSpriteList().add(sprite);
                } catch (IOException e) {
                    Log.e(SpriteActivity.TAG, Log.getStackTraceString(e));
                }
                if (SpriteActivity.this.onNewSpriteListener != null) {
                    SpriteActivity.this.onNewSpriteListener.addItem(sprite);
                }
            }
        });
        builder.setTitle(R.string.new_sprite_dialog_title).setNegativeButton(R.string.cancel, null).create().show();
    }

    private void addBackgroundFromUri(Uri uri) {
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        Sprite currentSprite = currentScene.getBackgroundSprite();
        File imageDirectory = new File(currentScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
        String name = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (name == null) {
            name = getString(R.string.background);
        } else {
            name = StorageOperations.getSanitizedFileName(name);
        }
        name = new UniqueNameProvider().getUniqueNameInNameables(name, currentSprite.getLookList());
        try {
            LookData look = new LookData(name, StorageOperations.copyUriToDir(getContentResolver(), uri, imageDirectory, name));
            currentSprite.getLookList().add(look);
            if (this.onNewLookListener != null) {
                this.onNewLookListener.addItem(look);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private void addLookFromUri(Uri uri) {
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        File imageDirectory = new File(currentScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
        String name = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (name == null) {
            name = getString(R.string.default_look_name);
        } else {
            name = StorageOperations.getSanitizedFileName(name);
        }
        name = new UniqueNameProvider().getUniqueNameInNameables(name, currentSprite.getLookList());
        try {
            LookData look = new LookData(name, StorageOperations.copyUriToDir(getContentResolver(), uri, imageDirectory, name));
            currentSprite.getLookList().add(look);
            if (this.onNewLookListener != null) {
                this.onNewLookListener.addItem(look);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private void addSoundFromUri(Uri uri) {
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        File soundDirectory = new File(currentScene.getDirectory(), Constants.SOUND_DIRECTORY_NAME);
        String name = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (name == null) {
            name = getString(R.string.default_sound_name);
        } else {
            name = StorageOperations.getSanitizedFileName(name);
        }
        name = new UniqueNameProvider().getUniqueNameInNameables(name, currentSprite.getSoundList());
        try {
            SoundInfo sound = new SoundInfo(name, StorageOperations.copyUriToDir(getContentResolver(), uri, soundDirectory, name));
            currentSprite.getSoundList().add(sound);
            if (this.onNewSoundListener != null) {
                this.onNewSoundListener.addItem(sound);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public void handleAddButton(View view) {
        if (getCurrentFragment() instanceof ScriptFragment) {
            ((ScriptFragment) getCurrentFragment()).handleAddButton();
        } else if (getCurrentFragment() instanceof DataListFragment) {
            handleAddUserDataButton();
        } else if (getCurrentFragment() instanceof LookListFragment) {
            handleAddLookButton();
        } else {
            if (getCurrentFragment() instanceof SoundListFragment) {
                handleAddSoundButton();
            }
        }
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
                        stringBuilder.append(SpriteActivity.this.getString(R.string.default_look_name));
                        stringBuilder.append(ARMediaManager.ARMEDIA_MANAGER_JPG);
                        new ImportFromCameraLauncher(SpriteActivity.this, Uri.fromFile(new File(file, stringBuilder.toString()))).startActivityForResult(3);
                        break;
                    case R.id.dialog_new_look_gallery:
                        new ImportFromFileLauncher(SpriteActivity.this, "image/*", SpriteActivity.this.getString(R.string.select_look_from_gallery)).startActivityForResult(2);
                        break;
                    case R.id.dialog_new_look_media_library:
                        new ImportFormMediaLibraryLauncher(SpriteActivity.this, FlavoredConstants.LIBRARY_LOOKS_URL).startActivityForResult(1);
                        break;
                    case R.id.dialog_new_look_paintroid:
                        new ImportFromPocketPaintLauncher(SpriteActivity.this).startActivityForResult(0);
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

    public void handleAddBackgroundButton() {
        String mediaLibraryUrl;
        View view = View.inflate(this, R.layout.dialog_new_look, null);
        final AlertDialog alertDialog = new AlertDialog$Builder(this).setTitle(R.string.new_look_dialog_title).setView(view).create();
        if (ProjectManager.getInstance().isCurrentProjectLandscapeMode()) {
            mediaLibraryUrl = FlavoredConstants.LIBRARY_BACKGROUNDS_URL_LANDSCAPE;
        } else {
            mediaLibraryUrl = FlavoredConstants.LIBRARY_BACKGROUNDS_URL_PORTRAIT;
        }
        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.dialog_new_look_camera:
                        File file = FlavoredConstants.DEFAULT_ROOT_DIRECTORY;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(SpriteActivity.this.getString(R.string.default_look_name));
                        stringBuilder.append(ARMediaManager.ARMEDIA_MANAGER_JPG);
                        new ImportFromCameraLauncher(SpriteActivity.this, Uri.fromFile(new File(file, stringBuilder.toString()))).startActivityForResult(7);
                        break;
                    case R.id.dialog_new_look_gallery:
                        new ImportFromFileLauncher(SpriteActivity.this, "image/*", SpriteActivity.this.getString(R.string.select_look_from_gallery)).startActivityForResult(6);
                        break;
                    case R.id.dialog_new_look_media_library:
                        new ImportFormMediaLibraryLauncher(SpriteActivity.this, mediaLibraryUrl).startActivityForResult(5);
                        break;
                    case R.id.dialog_new_look_paintroid:
                        new ImportFromPocketPaintLauncher(SpriteActivity.this).startActivityForResult(4);
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

    public void handleAddLookButton() {
        String mediaLibraryUrl;
        View view = View.inflate(this, R.layout.dialog_new_look, null);
        final AlertDialog alertDialog = new AlertDialog$Builder(this).setTitle(R.string.new_look_dialog_title).setView(view).create();
        if (!ProjectManager.getInstance().getCurrentSprite().equals(ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite())) {
            mediaLibraryUrl = FlavoredConstants.LIBRARY_LOOKS_URL;
        } else if (ProjectManager.getInstance().isCurrentProjectLandscapeMode()) {
            mediaLibraryUrl = FlavoredConstants.LIBRARY_BACKGROUNDS_URL_LANDSCAPE;
        } else {
            mediaLibraryUrl = FlavoredConstants.LIBRARY_BACKGROUNDS_URL_PORTRAIT;
        }
        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.dialog_new_look_camera:
                        File file = FlavoredConstants.DEFAULT_ROOT_DIRECTORY;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(SpriteActivity.this.getString(R.string.default_look_name));
                        stringBuilder.append(ARMediaManager.ARMEDIA_MANAGER_JPG);
                        new ImportFromCameraLauncher(SpriteActivity.this, Uri.fromFile(new File(file, stringBuilder.toString()))).startActivityForResult(11);
                        break;
                    case R.id.dialog_new_look_gallery:
                        new ImportFromFileLauncher(SpriteActivity.this, "image/*", SpriteActivity.this.getString(R.string.select_look_from_gallery)).startActivityForResult(10);
                        break;
                    case R.id.dialog_new_look_media_library:
                        new ImportFormMediaLibraryLauncher(SpriteActivity.this, mediaLibraryUrl).startActivityForResult(9);
                        break;
                    case R.id.dialog_new_look_paintroid:
                        new ImportFromPocketPaintLauncher(SpriteActivity.this).startActivityForResult(8);
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

    public void handleAddSoundButton() {
        View view = View.inflate(this, R.layout.dialog_new_sound, null);
        final AlertDialog alertDialog = new AlertDialog$Builder(this).setTitle(R.string.new_sound_dialog_title).setView(view).create();
        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.dialog_new_sound_gallery:
                        new ImportFromFileLauncher(SpriteActivity.this, "audio/*", SpriteActivity.this.getString(R.string.sound_select_source)).startActivityForResult(14);
                        break;
                    case R.id.dialog_new_sound_media_library:
                        new ImportFormMediaLibraryLauncher(SpriteActivity.this, FlavoredConstants.LIBRARY_SOUNDS_URL).startActivityForResult(13);
                        break;
                    case R.id.dialog_new_sound_pocketmusic:
                        SpriteActivity.this.startActivity(new Intent(SpriteActivity.this, PocketMusicActivity.class));
                        break;
                    case R.id.dialog_new_sound_recorder:
                        SpriteActivity.this.startActivityForResult(new Intent(SpriteActivity.this, SoundRecorderActivity.class), 12);
                        break;
                    default:
                        break;
                }
                alertDialog.dismiss();
            }
        };
        view.findViewById(R.id.dialog_new_sound_recorder).setOnClickListener(onClickListener);
        view.findViewById(R.id.dialog_new_sound_media_library).setOnClickListener(onClickListener);
        view.findViewById(R.id.dialog_new_sound_gallery).setOnClickListener(onClickListener);
        view.findViewById(R.id.dialog_new_sound_pocketmusic).setVisibility(0);
        view.findViewById(R.id.dialog_new_sound_pocketmusic).setOnClickListener(onClickListener);
        alertDialog.show();
    }

    public void handleAddUserDataButton() {
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        DataContainer dataContainer = currentScene.getDataContainer();
        View view = View.inflate(this, R.layout.dialog_new_user_data, null);
        CheckBox makeListCheckBox = (CheckBox) view.findViewById(R.id.make_list);
        RadioButton addToProjectUserDataRadioButton = (RadioButton) view.findViewById(R.id.global);
        makeListCheckBox.setVisibility(0);
        List variables = new ArrayList();
        variables.addAll(dataContainer.getProjectUserVariables());
        variables.addAll(dataContainer.getSpriteUserVariables(currentSprite));
        List lists = new ArrayList();
        lists.addAll(dataContainer.getProjectUserLists());
        lists.addAll(dataContainer.getSpriteUserLists(currentSprite));
        NewItemTextWatcher<UserData> textWatcher = new NewItemTextWatcher(variables);
        Builder textWatcher2 = new Builder(this).setTextWatcher(textWatcher);
        final RadioButton radioButton = addToProjectUserDataRadioButton;
        C21176 builder = r0;
        final CheckBox checkBox = makeListCheckBox;
        String string = getString(R.string.ok);
        final DataContainer dataContainer2 = dataContainer;
        Builder builder2 = textWatcher2;
        final Sprite sprite = currentSprite;
        C21176 c21176 = new TextInputDialog.OnClickListener() {
            public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
                boolean addToProjectUserData = radioButton.isChecked();
                if (checkBox.isChecked()) {
                    UserList userList = new UserList(textInput);
                    if (addToProjectUserData) {
                        dataContainer2.addUserList(userList);
                    } else {
                        dataContainer2.addUserList(sprite, userList);
                    }
                } else {
                    UserVariable userVariable = new UserVariable(textInput);
                    if (addToProjectUserData) {
                        dataContainer2.addUserVariable(userVariable);
                    } else {
                        dataContainer2.addUserVariable(sprite, userVariable);
                    }
                }
                if (SpriteActivity.this.getCurrentFragment() instanceof DataListFragment) {
                    ((DataListFragment) SpriteActivity.this.getCurrentFragment()).notifyDataSetChanged();
                }
            }
        };
        AlertDialog alertDialog = builder2.setPositiveButton(string, builder).setTitle(R.string.formula_editor_variable_dialog_title).setView(view).create();
        final AlertDialog alertDialog2 = alertDialog;
        final NewItemTextWatcher<UserData> newItemTextWatcher = textWatcher;
        final List list = lists;
        final List list2 = variables;
        makeListCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    alertDialog2.setTitle(SpriteActivity.this.getString(R.string.formula_editor_list_dialog_title));
                    newItemTextWatcher.setScope(list);
                    return;
                }
                alertDialog2.setTitle(SpriteActivity.this.getString(R.string.formula_editor_variable_dialog_title));
                newItemTextWatcher.setScope(list2);
            }
        });
        alertDialog.show();
    }

    public void handlePlayButton(View view) {
        boolean draggingActive = (getCurrentFragment() instanceof ScriptFragment) && ((ScriptFragment) getCurrentFragment()).getListView().isCurrentlyDragging();
        if (draggingActive) {
            ((ScriptFragment) getCurrentFragment()).getListView().animateHoveringBrick();
            return;
        }
        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        ProjectManager projectManager = ProjectManager.getInstance();
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        Scene defaultScene = projectManager.getCurrentProject().getDefaultScene();
        if (currentScene.getName().equals(defaultScene.getName())) {
            projectManager.setCurrentlyPlayingScene(defaultScene);
            projectManager.setStartScene(defaultScene);
            startPreStageActivity();
        } else {
            new PlaySceneDialog.Builder(this).setPositiveButton(R.string.play, new C19118()).create().show();
        }
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
}
