package org.catrobat.catroid.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.dialog.NewProjectDialogFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class ProjectListActivity extends BaseCastActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsFragment.setToChosenLanguage(this);
        setContentView(R.layout.activity_recycler);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.project_list_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomBar.hidePlayButton(this);
        loadFragment(new ProjectListFragment());
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, ProjectListFragment.TAG).commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_projects_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void handleAddButton(View view) {
        new NewProjectDialogFragment().show(getSupportFragmentManager(), NewProjectDialogFragment.TAG);
    }
}
