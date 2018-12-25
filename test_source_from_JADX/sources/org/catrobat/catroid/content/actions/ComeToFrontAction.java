package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;

public class ComeToFrontAction extends TemporalAction {
    private Sprite sprite;

    protected void update(float delta) {
        this.sprite.look.setZIndex((ProjectManager.getInstance().getCurrentProject().getSpriteListWithClones().size() + 1) - 1);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
