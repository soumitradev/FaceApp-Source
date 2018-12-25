package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.math.Vector2;

public class InputEvent extends Event {
    private int button;
    private char character;
    private int keyCode;
    private int pointer;
    private Actor relatedActor;
    private int scrollAmount;
    private float stageX;
    private float stageY;
    private Type type;

    public enum Type {
        touchDown,
        touchUp,
        touchDragged,
        mouseMoved,
        enter,
        exit,
        scrolled,
        keyDown,
        keyUp,
        keyTyped
    }

    public void reset() {
        super.reset();
        this.relatedActor = null;
        this.button = -1;
    }

    public float getStageX() {
        return this.stageX;
    }

    public void setStageX(float stageX) {
        this.stageX = stageX;
    }

    public float getStageY() {
        return this.stageY;
    }

    public void setStageY(float stageY) {
        this.stageY = stageY;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getPointer() {
        return this.pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public int getButton() {
        return this.button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public char getCharacter() {
        return this.character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public int getScrollAmount() {
        return this.scrollAmount;
    }

    public void setScrollAmount(int scrollAmount) {
        this.scrollAmount = scrollAmount;
    }

    public Actor getRelatedActor() {
        return this.relatedActor;
    }

    public void setRelatedActor(Actor relatedActor) {
        this.relatedActor = relatedActor;
    }

    public Vector2 toCoordinates(Actor actor, Vector2 actorCoords) {
        actorCoords.set(this.stageX, this.stageY);
        actor.stageToLocalCoordinates(actorCoords);
        return actorCoords;
    }

    public boolean isTouchFocusCancel() {
        if (this.stageX != -2.14748365E9f) {
            if (this.stageY != -2.14748365E9f) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return this.type.toString();
    }
}
