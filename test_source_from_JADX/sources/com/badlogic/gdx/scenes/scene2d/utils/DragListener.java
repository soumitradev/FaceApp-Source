package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class DragListener extends InputListener {
    private int button;
    private float deltaX;
    private float deltaY;
    private boolean dragging;
    private int pressedPointer = -1;
    private float stageTouchDownX = -1.0f;
    private float stageTouchDownY = -1.0f;
    private float tapSquareSize = 14.0f;
    private float touchDownX = -1.0f;
    private float touchDownY = -1.0f;

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (this.pressedPointer != -1) {
            return false;
        }
        if (pointer == 0 && this.button != -1 && button != this.button) {
            return false;
        }
        this.pressedPointer = pointer;
        this.touchDownX = x;
        this.touchDownY = y;
        this.stageTouchDownX = event.getStageX();
        this.stageTouchDownY = event.getStageY();
        return true;
    }

    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (pointer == this.pressedPointer) {
            if (!this.dragging && (Math.abs(this.touchDownX - x) > this.tapSquareSize || Math.abs(this.touchDownY - y) > this.tapSquareSize)) {
                this.dragging = true;
                dragStart(event, x, y, pointer);
                this.deltaX = x;
                this.deltaY = y;
            }
            if (this.dragging) {
                this.deltaX -= x;
                this.deltaY -= y;
                drag(event, x, y, pointer);
                this.deltaX = x;
                this.deltaY = y;
            }
        }
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == this.pressedPointer) {
            if (this.dragging) {
                dragStop(event, x, y, pointer);
            }
            cancel();
        }
    }

    public void dragStart(InputEvent event, float x, float y, int pointer) {
    }

    public void drag(InputEvent event, float x, float y, int pointer) {
    }

    public void dragStop(InputEvent event, float x, float y, int pointer) {
    }

    public void cancel() {
        this.dragging = false;
        this.pressedPointer = -1;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public void setTapSquareSize(float halfTapSquareSize) {
        this.tapSquareSize = halfTapSquareSize;
    }

    public float getTapSquareSize() {
        return this.tapSquareSize;
    }

    public float getTouchDownX() {
        return this.touchDownX;
    }

    public float getTouchDownY() {
        return this.touchDownY;
    }

    public float getStageTouchDownX() {
        return this.stageTouchDownX;
    }

    public float getStageTouchDownY() {
        return this.stageTouchDownY;
    }

    public float getDeltaX() {
        return this.deltaX;
    }

    public float getDeltaY() {
        return this.deltaY;
    }

    public int getButton() {
        return this.button;
    }

    public void setButton(int button) {
        this.button = button;
    }
}
