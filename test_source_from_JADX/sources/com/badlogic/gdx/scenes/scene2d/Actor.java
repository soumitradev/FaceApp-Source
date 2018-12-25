package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Pools;

public class Actor {
    private final Array<Action> actions = new Array(0);
    private final DelayedRemovalArray<EventListener> captureListeners = new DelayedRemovalArray(0);
    final Color color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    private boolean debug;
    float height;
    private final DelayedRemovalArray<EventListener> listeners = new DelayedRemovalArray(0);
    private String name;
    float originX;
    float originY;
    Group parent;
    float rotation;
    float scaleX = 1.0f;
    float scaleY = 1.0f;
    private Stage stage;
    private Touchable touchable = Touchable.enabled;
    private Object userObject;
    private boolean visible = true;
    float width;
    /* renamed from: x */
    float f7x;
    /* renamed from: y */
    float f8y;

    public void draw(Batch batch, float parentAlpha) {
    }

    public void act(float delta) {
        Array<Action> actions = this.actions;
        if (actions.size > 0) {
            if (this.stage != null && this.stage.getActionsRequestRendering()) {
                Gdx.graphics.requestRendering();
            }
            int i = 0;
            while (i < actions.size) {
                Action action = (Action) actions.get(i);
                if (action.act(delta) && i < actions.size) {
                    int actionIndex = ((Action) actions.get(i)) == action ? i : actions.indexOf(action, true);
                    if (actionIndex != -1) {
                        actions.removeIndex(actionIndex);
                        action.setActor(null);
                        i--;
                    }
                }
                i++;
            }
        }
    }

    public boolean fire(Event event) {
        if (event.getStage() == null) {
            event.setStage(getStage());
        }
        event.setTarget(this);
        Array<Group> ancestors = (Array) Pools.obtain(Array.class);
        for (Group parent = this.parent; parent != null; parent = parent.parent) {
            ancestors.add(parent);
        }
        try {
            boolean isCancelled;
            Object[] ancestorsArray = ancestors.items;
            boolean z = true;
            for (int i = ancestors.size - z; i >= 0; i--) {
                ancestorsArray[i].notify(event, z);
                if (event.isStopped()) {
                    z = event.isCancelled();
                    return z;
                }
            }
            notify(event, z);
            if (event.isStopped()) {
                isCancelled = event.isCancelled();
            } else {
                notify(event, false);
                if (!event.getBubbles()) {
                    isCancelled = event.isCancelled();
                } else if (event.isStopped()) {
                    isCancelled = event.isCancelled();
                } else {
                    int n = ancestors.size;
                    for (int i2 = 0; i2 < n; i2++) {
                        ((Group) ancestorsArray[i2]).notify(event, false);
                        if (event.isStopped()) {
                            isCancelled = event.isCancelled();
                            break;
                        }
                    }
                    isCancelled = event.isCancelled();
                }
            }
            ancestors.clear();
            Pools.free(ancestors);
            return isCancelled;
        } finally {
            ancestors.clear();
            Pools.free(ancestors);
        }
    }

    public boolean notify(Event event, boolean capture) {
        if (event.getTarget() == null) {
            throw new IllegalArgumentException("The event target cannot be null.");
        }
        DelayedRemovalArray<EventListener> listeners = capture ? this.captureListeners : this.listeners;
        if (listeners.size == 0) {
            return event.isCancelled();
        }
        event.setListenerActor(this);
        event.setCapture(capture);
        if (event.getStage() == null) {
            event.setStage(this.stage);
        }
        listeners.begin();
        int n = listeners.size;
        for (int i = 0; i < n; i++) {
            EventListener listener = (EventListener) listeners.get(i);
            if (listener.handle(event)) {
                event.handle();
                if (event instanceof InputEvent) {
                    InputEvent inputEvent = (InputEvent) event;
                    if (inputEvent.getType() == Type.touchDown) {
                        event.getStage().addTouchFocus(listener, this, inputEvent.getTarget(), inputEvent.getPointer(), inputEvent.getButton());
                    }
                }
            }
        }
        listeners.end();
        return event.isCancelled();
    }

    public Actor hit(float x, float y, boolean touchable) {
        Actor actor = null;
        if (touchable && this.touchable != Touchable.enabled) {
            return null;
        }
        if (x >= 0.0f && x < this.width && y >= 0.0f && y < this.height) {
            actor = this;
        }
        return actor;
    }

    public boolean remove() {
        if (this.parent != null) {
            return this.parent.removeActor(this, true);
        }
        return false;
    }

    public boolean addListener(EventListener listener) {
        if (this.listeners.contains(listener, true)) {
            return false;
        }
        this.listeners.add(listener);
        return true;
    }

    public boolean removeListener(EventListener listener) {
        return this.listeners.removeValue(listener, true);
    }

    public Array<EventListener> getListeners() {
        return this.listeners;
    }

    public boolean addCaptureListener(EventListener listener) {
        if (!this.captureListeners.contains(listener, true)) {
            this.captureListeners.add(listener);
        }
        return true;
    }

    public boolean removeCaptureListener(EventListener listener) {
        return this.captureListeners.removeValue(listener, true);
    }

    public Array<EventListener> getCaptureListeners() {
        return this.captureListeners;
    }

    public void addAction(Action action) {
        action.setActor(this);
        this.actions.add(action);
        if (this.stage != null && this.stage.getActionsRequestRendering()) {
            Gdx.graphics.requestRendering();
        }
    }

    public void removeAction(Action action) {
        if (this.actions.removeValue(action, true)) {
            action.setActor(null);
        }
    }

    public Array<Action> getActions() {
        return this.actions;
    }

    public void clearActions() {
        for (int i = this.actions.size - 1; i >= 0; i--) {
            ((Action) this.actions.get(i)).setActor(null);
        }
        this.actions.clear();
    }

    public void clearListeners() {
        this.listeners.clear();
        this.captureListeners.clear();
    }

    public void clear() {
        clearActions();
        clearListeners();
    }

    public Stage getStage() {
        return this.stage;
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isDescendantOf(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }
        for (Actor parent = this; parent != null; parent = parent.parent) {
            if (parent == actor) {
                return true;
            }
        }
        return false;
    }

    public boolean isAscendantOf(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }
        while (actor != null) {
            if (actor == this) {
                return true;
            }
            actor = actor.parent;
        }
        return false;
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public Group getParent() {
        return this.parent;
    }

    protected void setParent(Group parent) {
        this.parent = parent;
    }

    public boolean isTouchable() {
        return this.touchable == Touchable.enabled;
    }

    public Touchable getTouchable() {
        return this.touchable;
    }

    public void setTouchable(Touchable touchable) {
        this.touchable = touchable;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Object getUserObject() {
        return this.userObject;
    }

    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }

    public float getX() {
        return this.f7x;
    }

    public float getX(int alignment) {
        float x = this.f7x;
        if ((alignment & 16) != 0) {
            return x + this.width;
        }
        if ((alignment & 8) == 0) {
            return x + (this.width / 2.0f);
        }
        return x;
    }

    public void setX(float x) {
        if (this.f7x != x) {
            this.f7x = x;
            positionChanged();
        }
    }

    public float getY() {
        return this.f8y;
    }

    public void setY(float y) {
        if (this.f8y != y) {
            this.f8y = y;
            positionChanged();
        }
    }

    public float getY(int alignment) {
        float y = this.f8y;
        if ((alignment & 2) != 0) {
            return y + this.height;
        }
        if ((alignment & 4) == 0) {
            return y + (this.height / 2.0f);
        }
        return y;
    }

    public void setPosition(float x, float y) {
        if (this.f7x != x || this.f8y != y) {
            this.f7x = x;
            this.f8y = y;
            positionChanged();
        }
    }

    public void setPosition(float x, float y, int alignment) {
        if ((alignment & 16) != 0) {
            x -= this.width;
        } else if ((alignment & 8) == 0) {
            x -= this.width / 2.0f;
        }
        if ((alignment & 2) != 0) {
            y -= this.height;
        } else if ((alignment & 4) == 0) {
            y -= this.height / 2.0f;
        }
        if (this.f7x != x || this.f8y != y) {
            this.f7x = x;
            this.f8y = y;
            positionChanged();
        }
    }

    public void moveBy(float x, float y) {
        if (x != 0.0f || y != 0.0f) {
            this.f7x += x;
            this.f8y += y;
            positionChanged();
        }
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        float oldWidth = this.width;
        this.width = width;
        if (width != oldWidth) {
            sizeChanged();
        }
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        float oldHeight = this.height;
        this.height = height;
        if (height != oldHeight) {
            sizeChanged();
        }
    }

    public float getTop() {
        return this.f8y + this.height;
    }

    public float getRight() {
        return this.f7x + this.width;
    }

    protected void positionChanged() {
    }

    protected void sizeChanged() {
    }

    public void setSize(float width, float height) {
        float oldWidth = this.width;
        float oldHeight = this.height;
        this.width = width;
        this.height = height;
        if (width != oldWidth || height != oldHeight) {
            sizeChanged();
        }
    }

    public void sizeBy(float size) {
        this.width += size;
        this.height += size;
        sizeChanged();
    }

    public void sizeBy(float width, float height) {
        this.width += width;
        this.height += height;
        sizeChanged();
    }

    public void setBounds(float x, float y, float width, float height) {
        if (!(this.f7x == x && this.f8y == y)) {
            this.f7x = x;
            this.f8y = y;
            positionChanged();
        }
        if (this.width != width || this.height != height) {
            this.width = width;
            this.height = height;
            sizeChanged();
        }
    }

    public float getOriginX() {
        return this.originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return this.originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
    }

    public void setOrigin(int alignment) {
        if ((alignment & 8) != 0) {
            this.originX = 0.0f;
        } else if ((alignment & 16) != 0) {
            this.originX = this.width;
        } else {
            this.originX = this.width / 2.0f;
        }
        if ((alignment & 4) != 0) {
            this.originY = 0.0f;
        } else if ((alignment & 2) != 0) {
            this.originY = this.height;
        } else {
            this.originY = this.height / 2.0f;
        }
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void setScale(float scaleXY) {
        this.scaleX = scaleXY;
        this.scaleY = scaleXY;
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public void scaleBy(float scale) {
        this.scaleX += scale;
        this.scaleY += scale;
    }

    public void scaleBy(float scaleX, float scaleY) {
        this.scaleX += scaleX;
        this.scaleY += scaleY;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setRotation(float degrees) {
        this.rotation = degrees;
    }

    public void rotateBy(float amountInDegrees) {
        this.rotation += amountInDegrees;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    public Color getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void toFront() {
        setZIndex(Integer.MAX_VALUE);
    }

    public void toBack() {
        setZIndex(0);
    }

    public void setZIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("ZIndex cannot be < 0.");
        }
        Group parent = this.parent;
        if (parent != null) {
            Array<Actor> children = parent.children;
            if (children.size != 1 && children.removeValue(this, true)) {
                if (index >= children.size) {
                    children.add(this);
                } else {
                    children.insert(index, this);
                }
            }
        }
    }

    public int getZIndex() {
        Group parent = this.parent;
        if (parent == null) {
            return -1;
        }
        return parent.children.indexOf(this, true);
    }

    public boolean clipBegin() {
        return clipBegin(this.f7x, this.f8y, this.width, this.height);
    }

    public boolean clipBegin(float x, float y, float width, float height) {
        if (width > 0.0f) {
            if (height > 0.0f) {
                Rectangle tableBounds = Rectangle.tmp;
                tableBounds.f12x = x;
                tableBounds.f13y = y;
                tableBounds.width = width;
                tableBounds.height = height;
                Rectangle scissorBounds = (Rectangle) Pools.obtain(Rectangle.class);
                this.stage.calculateScissors(tableBounds, scissorBounds);
                if (ScissorStack.pushScissors(scissorBounds)) {
                    return true;
                }
                Pools.free(scissorBounds);
                return false;
            }
        }
        return false;
    }

    public void clipEnd() {
        Pools.free(ScissorStack.popScissors());
    }

    public Vector2 screenToLocalCoordinates(Vector2 screenCoords) {
        Stage stage = this.stage;
        if (stage == null) {
            return screenCoords;
        }
        return stageToLocalCoordinates(stage.screenToStageCoordinates(screenCoords));
    }

    public Vector2 stageToLocalCoordinates(Vector2 stageCoords) {
        if (this.parent != null) {
            this.parent.stageToLocalCoordinates(stageCoords);
        }
        parentToLocalCoordinates(stageCoords);
        return stageCoords;
    }

    public Vector2 localToStageCoordinates(Vector2 localCoords) {
        return localToAscendantCoordinates(null, localCoords);
    }

    public Vector2 localToParentCoordinates(Vector2 localCoords) {
        float rotation = -this.rotation;
        float scaleX = this.scaleX;
        float scaleY = this.scaleY;
        float x = this.f7x;
        float y = this.f8y;
        float cos;
        float sin;
        if (rotation != 0.0f) {
            cos = (float) Math.cos((double) (rotation * 0.017453292f));
            sin = (float) Math.sin((double) (0.017453292f * rotation));
            float originX = this.originX;
            float originY = this.originY;
            float tox = (localCoords.f16x - originX) * scaleX;
            float toy = (localCoords.f17y - originY) * scaleY;
            localCoords.f16x = (((tox * cos) + (toy * sin)) + originX) + x;
            localCoords.f17y = ((((-sin) * tox) + (toy * cos)) + originY) + y;
        } else if (scaleX == 1.0f && scaleY == 1.0f) {
            localCoords.f16x += x;
            localCoords.f17y += y;
        } else {
            sin = this.originX;
            cos = this.originY;
            localCoords.f16x = (((localCoords.f16x - sin) * scaleX) + sin) + x;
            localCoords.f17y = (((localCoords.f17y - cos) * scaleY) + cos) + y;
        }
        return localCoords;
    }

    public Vector2 localToAscendantCoordinates(Actor ascendant, Vector2 localCoords) {
        Actor actor = this;
        while (actor != null) {
            actor.localToParentCoordinates(localCoords);
            actor = actor.parent;
            if (actor == ascendant) {
                break;
            }
        }
        return localCoords;
    }

    public Vector2 parentToLocalCoordinates(Vector2 parentCoords) {
        float rotation = this.rotation;
        float scaleX = this.scaleX;
        float scaleY = this.scaleY;
        float childX = this.f7x;
        float childY = this.f8y;
        float cos;
        float sin;
        if (rotation != 0.0f) {
            cos = (float) Math.cos((double) (rotation * 0.017453292f));
            sin = (float) Math.sin((double) (0.017453292f * rotation));
            float originX = this.originX;
            float originY = this.originY;
            float tox = (parentCoords.f16x - childX) - originX;
            float toy = (parentCoords.f17y - childY) - originY;
            parentCoords.f16x = (((tox * cos) + (toy * sin)) / scaleX) + originX;
            parentCoords.f17y = ((((-sin) * tox) + (toy * cos)) / scaleY) + originY;
        } else if (scaleX == 1.0f && scaleY == 1.0f) {
            parentCoords.f16x -= childX;
            parentCoords.f17y -= childY;
        } else {
            sin = this.originX;
            cos = this.originY;
            parentCoords.f16x = (((parentCoords.f16x - childX) - sin) / scaleX) + sin;
            parentCoords.f17y = (((parentCoords.f17y - childY) - cos) / scaleY) + cos;
        }
        return parentCoords;
    }

    public void drawDebug(ShapeRenderer shapes) {
        drawDebugBounds(shapes);
    }

    protected void drawDebugBounds(ShapeRenderer shapes) {
        if (this.debug) {
            shapes.set(ShapeType.Line);
            shapes.setColor(this.stage.getDebugColor());
            shapes.rect(this.f7x, this.f8y, this.originX, this.originY, this.width, this.height, this.scaleX, this.scaleY, this.rotation);
        }
    }

    public void setDebug(boolean enabled) {
        this.debug = enabled;
        if (enabled) {
            Stage.debug = true;
        }
    }

    public boolean getDebug() {
        return this.debug;
    }

    public Actor debug() {
        setDebug(true);
        return this;
    }

    public String toString() {
        String name = this.name;
        if (name != null) {
            return name;
        }
        name = getClass().getName();
        int dotIndex = name.lastIndexOf(46);
        if (dotIndex != -1) {
            return name.substring(dotIndex + 1);
        }
        return name;
    }
}
