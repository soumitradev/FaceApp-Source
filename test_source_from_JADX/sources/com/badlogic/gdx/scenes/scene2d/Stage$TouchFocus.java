package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.utils.Pool.Poolable;

public final class Stage$TouchFocus implements Poolable {
    int button;
    EventListener listener;
    Actor listenerActor;
    int pointer;
    Actor target;

    public void reset() {
        this.listenerActor = null;
        this.listener = null;
    }
}
