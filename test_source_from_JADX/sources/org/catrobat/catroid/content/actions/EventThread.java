package org.catrobat.catroid.content.actions;

import android.support.annotation.NonNull;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.util.Iterator;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;

public class EventThread extends ScriptSequenceAction {
    private NotifyEventWaiterAction notifyAction;

    public EventThread(@NonNull Script script) {
        super(script);
    }

    public EventThread(@NonNull EventThread originalThread, @NonNull Sprite sprite, @NonNull EventWrapper event) {
        super(originalThread.script);
        Iterator it = originalThread.getActions().iterator();
        while (it.hasNext()) {
            addAction((Action) it.next());
        }
        this.notifyAction = (NotifyEventWaiterAction) sprite.getActionFactory().createNotifyEventWaiterAction(sprite, event);
    }

    public void notifyWaiter() {
        if (this.notifyAction != null) {
            this.notifyAction.act(1.0f);
        }
    }

    public boolean act(float delta) {
        if (!super.act(delta)) {
            return false;
        }
        notifyWaiter();
        return true;
    }

    public void reset() {
        notifyWaiter();
        super.reset();
    }

    public EventThread clone() {
        return (EventThread) ActionFactory.createEventThread(this.script);
    }

    public void setNotifyAction(NotifyEventWaiterAction notifyAction) {
        this.notifyAction = notifyAction;
    }
}
