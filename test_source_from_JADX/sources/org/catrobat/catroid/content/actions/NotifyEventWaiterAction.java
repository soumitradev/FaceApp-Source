package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Sprite;

public class NotifyEventWaiterAction extends Action {
    private EventWrapper event;
    private boolean firstStart = true;
    private Sprite sprite;

    public boolean act(float delta) {
        if (this.firstStart) {
            this.event.notify(this.sprite);
            this.firstStart = false;
        }
        return true;
    }

    public void setEvent(EventWrapper event) {
        this.event = event;
    }

    public EventWrapper getEvent() {
        return this.event;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void restart() {
        super.restart();
        this.firstStart = true;
    }

    public void reset() {
        this.event.notify(this.sprite);
        super.reset();
    }
}
