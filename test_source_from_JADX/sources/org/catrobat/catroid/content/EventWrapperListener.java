package org.catrobat.catroid.content;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import org.catrobat.catroid.content.actions.EventThread;

public class EventWrapperListener implements EventListener {
    private final Look look;

    EventWrapperListener(Look look) {
        this.look = look;
    }

    public boolean handle(Event event) {
        if (!(event instanceof EventWrapper)) {
            return false;
        }
        handleEvent((EventWrapper) event);
        return true;
    }

    private void handleEvent(EventWrapper event) {
        for (EventThread threadToBeStarted : this.look.sprite.getIdToEventThreadMap().get(event.eventId)) {
            EventThread threadToBeStarted2;
            if (event.waitMode == 0) {
                event.addSpriteToWaitFor(this.look.sprite);
                threadToBeStarted2 = new EventThread(threadToBeStarted2, this.look.sprite, event);
            }
            this.look.stopThreadWithScript(threadToBeStarted2.getScript());
            this.look.startThread(threadToBeStarted2);
        }
    }
}
