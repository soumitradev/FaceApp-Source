package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.SetBackgroundEventId;

public class NextLookAction extends TemporalAction {
    static final int NEXT = 1;
    static final int PREVIOUS = -1;
    protected int direction = 1;
    private Sprite sprite;

    protected void update(float delta) {
        List<LookData> lookDataList = this.sprite.getLookList();
        int lookDataListSize = lookDataList.size();
        if (lookDataListSize > 0 && this.sprite.look.getLookData() != null) {
            LookData lookData = (LookData) lookDataList.get(((lookDataList.indexOf(this.sprite.look.getLookData()) + this.direction) + lookDataListSize) % lookDataListSize);
            this.sprite.look.setLookData(lookData);
            if (this.sprite.isBackgroundSprite()) {
                ProjectManager.getInstance().getCurrentProject().fireToAllSprites(new EventWrapper(new SetBackgroundEventId(this.sprite, lookData), 1));
            }
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
