package org.catrobat.catroid.ui;

import android.view.MenuItem;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.ui.adapter.BrickAdapter;

public class UserBrickSpriteActivity extends SpriteActivity {
    public void setupBrickAdapter(BrickAdapter adapter) {
        adapter.setUserBrick(ProjectManager.getInstance().getCurrentUserBrick());
        adapter.updateProjectBrickList();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }
}
