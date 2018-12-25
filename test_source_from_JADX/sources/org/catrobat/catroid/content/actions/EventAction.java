package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import java.util.List;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Sprite;

public class EventAction extends Action {
    protected EventWrapper event;
    boolean firstStart = true;
    private List<Sprite> receivingSprites;

    public boolean act(float delta) {
        if (this.firstStart) {
            this.firstStart = false;
            for (Sprite spriteOfList : this.receivingSprites) {
                spriteOfList.look.fire(this.event);
            }
        }
        List<Sprite> spritesToWaitFor = this.event.getSpritesToWaitFor();
        if (spritesToWaitFor != null) {
            if (spritesToWaitFor.size() != 0) {
                return false;
            }
        }
        return true;
    }

    public void restart() {
        this.firstStart = true;
    }

    public void setEvent(EventWrapper event) {
        this.event = event;
    }

    public void setReceivingSprites(List<Sprite> receivingSprites) {
        this.receivingSprites = receivingSprites;
    }
}
