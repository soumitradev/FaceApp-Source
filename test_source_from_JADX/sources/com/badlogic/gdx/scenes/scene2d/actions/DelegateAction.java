package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import org.catrobat.catroid.common.Constants;

public abstract class DelegateAction extends Action {
    protected Action action;

    protected abstract boolean delegate(float f);

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return this.action;
    }

    public final boolean act(float delta) {
        Pool pool = getPool();
        setPool(null);
        try {
            boolean delegate = delegate(delta);
            return delegate;
        } finally {
            setPool(pool);
        }
    }

    public void restart() {
        if (this.action != null) {
            this.action.restart();
        }
    }

    public void reset() {
        super.reset();
        this.action = null;
    }

    public void setActor(Actor actor) {
        if (this.action != null) {
            this.action.setActor(actor);
        }
        super.setActor(actor);
    }

    public void setTarget(Actor target) {
        if (this.action != null) {
            this.action.setTarget(target);
        }
        super.setTarget(target);
    }

    public String toString() {
        String str;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        if (this.action == null) {
            str = "";
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(Constants.OPENING_BRACE);
            stringBuilder2.append(this.action);
            stringBuilder2.append(")");
            str = stringBuilder2.toString();
        }
        stringBuilder.append(str);
        return stringBuilder.toString();
    }
}
