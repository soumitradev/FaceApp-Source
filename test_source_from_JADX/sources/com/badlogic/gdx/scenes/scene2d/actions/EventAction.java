package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public abstract class EventAction<T extends Event> extends Action {
    boolean active;
    final Class<? extends T> eventClass;
    private final EventListener listener = new C07851();
    boolean result;

    /* renamed from: com.badlogic.gdx.scenes.scene2d.actions.EventAction$1 */
    class C07851 implements EventListener {
        C07851() {
        }

        public boolean handle(Event event) {
            if (EventAction.this.active) {
                if (ClassReflection.isInstance(EventAction.this.eventClass, event)) {
                    EventAction.this.result = EventAction.this.handle(event);
                    return EventAction.this.result;
                }
            }
            return false;
        }
    }

    public abstract boolean handle(T t);

    public EventAction(Class<? extends T> eventClass) {
        this.eventClass = eventClass;
    }

    public void restart() {
        this.result = false;
        this.active = false;
    }

    public void setTarget(Actor newTarget) {
        if (this.target != null) {
            this.target.removeListener(this.listener);
        }
        super.setTarget(newTarget);
        if (newTarget != null) {
            newTarget.addListener(this.listener);
        }
    }

    public boolean act(float delta) {
        this.active = true;
        return this.result;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
