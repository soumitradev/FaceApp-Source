package org.catrobat.catroid.content;

import com.badlogic.gdx.scenes.scene2d.Event;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.content.eventids.EventId;

public class EventWrapper extends Event {
    public static final int NO_WAIT = 1;
    public static final int WAIT = 0;
    final EventId eventId;
    private List<Sprite> spritesToWaitFor;
    final int waitMode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface WaitMode {
    }

    public EventWrapper(EventId eventId, int waitMode) {
        this.eventId = eventId;
        this.waitMode = waitMode;
        if (waitMode == 0) {
            this.spritesToWaitFor = new ArrayList();
        }
    }

    public void notify(Sprite sprite) {
        this.spritesToWaitFor.remove(sprite);
    }

    void addSpriteToWaitFor(Sprite sprite) {
        this.spritesToWaitFor.add(sprite);
    }

    public List<Sprite> getSpritesToWaitFor() {
        return this.spritesToWaitFor;
    }
}
