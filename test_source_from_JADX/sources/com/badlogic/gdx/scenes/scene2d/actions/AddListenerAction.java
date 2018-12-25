package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public class AddListenerAction extends Action {
    private boolean capture;
    private EventListener listener;

    public boolean act(float delta) {
        if (this.capture) {
            this.target.addCaptureListener(this.listener);
        } else {
            this.target.addListener(this.listener);
        }
        return true;
    }

    public EventListener getListener() {
        return this.listener;
    }

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    public boolean getCapture() {
        return this.capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public void reset() {
        super.reset();
        this.listener = null;
    }
}
