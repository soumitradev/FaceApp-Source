package org.catrobat.catroid.ui.recyclerview.adapter;

import java.util.List;
import java.util.Locale;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedVH;

public class SceneAdapter extends ExtendedRVAdapter<Scene> {
    public SceneAdapter(List<Scene> items) {
        super(items);
    }

    public void onBindViewHolder(ExtendedVH holder, int position) {
        ProjectAndSceneScreenshotLoader loader = new ProjectAndSceneScreenshotLoader(holder.itemView.getContext());
        Scene item = (Scene) this.items.get(position);
        String projectName = ProjectManager.getInstance().getCurrentProject().getName();
        holder.title.setText(item.getName());
        loader.loadAndShowScreenshot(projectName, item.getName(), false, holder.image);
        if (this.showDetails) {
            holder.details.setText(String.format(Locale.getDefault(), holder.itemView.getContext().getString(R.string.scene_details), new Object[]{Integer.valueOf(item.getSpriteList().size()), Integer.valueOf(getLookCount(item)), Integer.valueOf(getSoundCount(item))}));
            holder.details.setVisibility(0);
            return;
        }
        holder.details.setVisibility(8);
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        boolean moved = super.onItemMove(fromPosition, toPosition);
        ProjectManager.getInstance().setCurrentlyEditedScene((Scene) this.items.get(0));
        return moved;
    }

    private int getLookCount(Scene scene) {
        int lookCount = 0;
        for (Sprite sprite : scene.getSpriteList()) {
            lookCount += sprite.getLookList().size();
        }
        return lookCount;
    }

    private int getSoundCount(Scene scene) {
        int soundCount = 0;
        for (Sprite sprite : scene.getSpriteList()) {
            soundCount += sprite.getSoundList().size();
        }
        return soundCount;
    }
}
