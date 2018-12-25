package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.math.Vector2;

public class InputListener implements EventListener {
    private static final Vector2 tmpCoords = new Vector2();

    public boolean handle(Event e) {
        if (!(e instanceof InputEvent)) {
            return false;
        }
        InputEvent event = (InputEvent) e;
        switch (InputListener$1.$SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[event.getType().ordinal()]) {
            case 1:
                return keyDown(event, event.getKeyCode());
            case 2:
                return keyUp(event, event.getKeyCode());
            case 3:
                return keyTyped(event, event.getCharacter());
            default:
                event.toCoordinates(event.getListenerActor(), tmpCoords);
                switch (InputListener$1.$SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[event.getType().ordinal()]) {
                    case 4:
                        return touchDown(event, tmpCoords.f16x, tmpCoords.f17y, event.getPointer(), event.getButton());
                    case 5:
                        touchUp(event, tmpCoords.f16x, tmpCoords.f17y, event.getPointer(), event.getButton());
                        return true;
                    case 6:
                        touchDragged(event, tmpCoords.f16x, tmpCoords.f17y, event.getPointer());
                        return true;
                    case 7:
                        return mouseMoved(event, tmpCoords.f16x, tmpCoords.f17y);
                    case 8:
                        return scrolled(event, tmpCoords.f16x, tmpCoords.f17y, event.getScrollAmount());
                    case 9:
                        enter(event, tmpCoords.f16x, tmpCoords.f17y, event.getPointer(), event.getRelatedActor());
                        return false;
                    case 10:
                        exit(event, tmpCoords.f16x, tmpCoords.f17y, event.getPointer(), event.getRelatedActor());
                        return false;
                    default:
                        return false;
                }
        }
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return false;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
    }

    public void touchDragged(InputEvent event, float x, float y, int pointer) {
    }

    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }

    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
    }

    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
    }

    public boolean scrolled(InputEvent event, float x, float y, int amount) {
        return false;
    }

    public boolean keyDown(InputEvent event, int keycode) {
        return false;
    }

    public boolean keyUp(InputEvent event, int keycode) {
        return false;
    }

    public boolean keyTyped(InputEvent event, char character) {
        return false;
    }
}
