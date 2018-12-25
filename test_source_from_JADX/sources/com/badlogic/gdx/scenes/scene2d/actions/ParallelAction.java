package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import org.catrobat.catroid.common.Constants;

public class ParallelAction extends Action {
    Array<Action> actions = new Array(4);
    private boolean complete;

    public ParallelAction(Action action1) {
        addAction(action1);
    }

    public ParallelAction(Action action1, Action action2) {
        addAction(action1);
        addAction(action2);
    }

    public ParallelAction(Action action1, Action action2, Action action3) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
    }

    public ParallelAction(Action action1, Action action2, Action action3, Action action4) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
    }

    public ParallelAction(Action action1, Action action2, Action action3, Action action4, Action action5) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
        addAction(action5);
    }

    public boolean act(float delta) {
        boolean z = true;
        if (this.complete) {
            return true;
        }
        this.complete = true;
        Pool pool = getPool();
        setPool(null);
        try {
            Array<Action> actions = this.actions;
            int n = actions.size;
            for (int i = 0; i < n && this.actor != null; i++) {
                if (!((Action) actions.get(i)).act(delta)) {
                    this.complete = false;
                }
                if (this.actor == null) {
                    break;
                }
            }
            z = this.complete;
            setPool(pool);
            return z;
        } catch (Throwable th) {
            setPool(pool);
        }
    }

    public void restart() {
        this.complete = false;
        Array<Action> actions = this.actions;
        int n = actions.size;
        for (int i = 0; i < n; i++) {
            ((Action) actions.get(i)).restart();
        }
    }

    public void reset() {
        super.reset();
        this.actions.clear();
    }

    public void addAction(Action action) {
        this.actions.add(action);
        if (this.actor != null) {
            action.setActor(this.actor);
        }
    }

    public void setActor(Actor actor) {
        Array<Action> actions = this.actions;
        int n = actions.size;
        for (int i = 0; i < n; i++) {
            ((Action) actions.get(i)).setActor(actor);
        }
        super.setActor(actor);
    }

    public Array<Action> getActions() {
        return this.actions;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(super.toString());
        buffer.append(Constants.REMIX_URL_PREFIX_REPLACE_INDICATOR);
        Array<Action> actions = this.actions;
        int n = actions.size;
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            buffer.append(actions.get(i));
        }
        buffer.append(Constants.REMIX_URL_SUFIX_REPLACE_INDICATOR);
        return buffer.toString();
    }
}
