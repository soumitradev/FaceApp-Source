package org.catrobat.catroid.ui;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.drone.ardrone.DroneServiceWrapper;
import org.catrobat.catroid.drone.ardrone.DroneStageActivity;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.stage.PreStageActivity;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.recyclerview.RVButton;
import org.catrobat.catroid.ui.recyclerview.adapter.ButtonAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.ButtonAdapter.OnItemClickListener;
import org.catrobat.catroid.ui.recyclerview.dialog.PlaySceneDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.RenameItemTextWatcher;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class SpriteAttributesActivity extends BaseActivity implements OnItemClickListener {
    private static final int LOOKS = 1;
    private static final int NFC_TAGS = 3;
    private static final int SCRIPTS = 0;
    private static final int SOUNDS = 2;

    /* renamed from: org.catrobat.catroid.ui.SpriteAttributesActivity$2 */
    class C19122 implements OnClickListener {
        C19122() {
        }

        public void onClick(DialogInterface dialog, int which) {
            SpriteAttributesActivity.this.startPreStageActivity();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isFinishing()) {
            SettingsFragment.setToChosenLanguage(this);
            setContentView(R.layout.activity_sprite_attributes);
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            ButtonAdapter adapter = new ButtonAdapter(getItems());
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));
            BottomBar.hideAddButton(this);
            updateActionBarTitle();
        }
    }

    private void updateActionBarTitle() {
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        if (ProjectManager.getInstance().getCurrentProject().getSceneList().size() == 1) {
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

    private List<RVButton> getItems() {
        List<RVButton> items = new ArrayList();
        items.add(new RVButton(0, ContextCompat.getDrawable(this, R.drawable.ic_program_menu_scripts), getString(R.string.scripts)));
        if (ProjectManager.getInstance().getCurrentSpritePosition() == 0) {
            items.add(new RVButton(1, ContextCompat.getDrawable(this, R.drawable.ic_program_menu_looks), getString(R.string.backgrounds)));
        } else {
            items.add(new RVButton(1, ContextCompat.getDrawable(this, R.drawable.ic_program_menu_looks), getString(R.string.looks)));
        }
        items.add(new RVButton(2, ContextCompat.getDrawable(this, R.drawable.ic_program_menu_sounds), getString(R.string.sounds)));
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(SettingsFragment.SETTINGS_SHOW_NFC_BRICKS, false)) {
            items.add(new RVButton(3, ContextCompat.getDrawable(this, R.drawable.ic_program_menu_nfc), getString(R.string.nfctags)));
        }
        return items;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.menu_rename_sprite) {
            return super.onOptionsItemSelected(item);
        }
        showRenameDialog();
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (ProjectManager.getInstance().getCurrentSpritePosition() == 0) {
            return super.onCreateOptionsMenu(menu);
        }
        getMenuInflater().inflate(R.menu.menu_program_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showRenameDialog() {
        Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        final Sprite item = ProjectManager.getInstance().getCurrentSprite();
        Builder builder = new Builder(this);
        builder.setHint(getString(R.string.sprite_name_label)).setText(item.getName()).setTextWatcher(new RenameItemTextWatcher(item, currentScene.getSpriteList())).setPositiveButton(getString(R.string.rename), new TextInputDialog.OnClickListener() {
            public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
                SpriteAttributesActivity.this.renameItem(item, textInput);
            }
        });
        builder.setTitle(R.string.rename_sprite_dialog).setNegativeButton(R.string.cancel, null).create().show();
    }

    private void renameItem(Sprite item, String name) {
        if (!item.getName().equals(name)) {
            item.setName(name);
        }
        updateActionBarTitle();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 101 || resultCode != -1) {
            return;
        }
        if (DroneServiceWrapper.checkARDroneAvailability()) {
            startActivity(new Intent(this, DroneStageActivity.class));
        } else {
            startActivity(new Intent(this, StageActivity.class));
        }
    }

    public void onItemClick(int id) {
        switch (id) {
            case 0:
                startScriptActivity(0);
                return;
            case 1:
                startScriptActivity(1);
                return;
            case 2:
                startScriptActivity(2);
                return;
            case 3:
                startScriptActivity(3);
                return;
            default:
                return;
        }
    }

    private void startScriptActivity(int fragmentPosition) {
        Intent intent = new Intent(this, SpriteActivity.class);
        intent.putExtra("fragmentPosition", fragmentPosition);
        startActivity(intent);
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
        new PlaySceneDialog.Builder(this).setPositiveButton(R.string.play, new C19122()).create().show();
    }

    public void startPreStageActivity() {
        startActivityForResult(new Intent(this, PreStageActivity.class), 101);
    }
}
