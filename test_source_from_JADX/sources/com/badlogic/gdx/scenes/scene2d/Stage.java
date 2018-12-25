package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Stage extends InputAdapter implements Disposable {
    static boolean debug;
    private boolean actionsRequestRendering;
    private final Batch batch;
    private boolean debugAll;
    private final Color debugColor;
    private boolean debugInvisible;
    private boolean debugParentUnderMouse;
    private ShapeRenderer debugShapes;
    private Debug debugTableUnderMouse;
    private boolean debugUnderMouse;
    private Actor keyboardFocus;
    private Actor mouseOverActor;
    private int mouseScreenX;
    private int mouseScreenY;
    private boolean ownsBatch;
    private final Actor[] pointerOverActors;
    private final int[] pointerScreenX;
    private final int[] pointerScreenY;
    private final boolean[] pointerTouched;
    private final Group root;
    private Actor scrollFocus;
    private final Vector2 tempCoords;
    private final SnapshotArray<Stage$TouchFocus> touchFocuses;
    private Viewport viewport;

    public Stage() {
        this(new ScalingViewport(Scaling.stretch, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight(), new OrthographicCamera()), new SpriteBatch());
        this.ownsBatch = true;
    }

    public Stage(Viewport viewport) {
        this(viewport, new SpriteBatch());
        this.ownsBatch = true;
    }

    public Stage(Viewport viewport, Batch batch) {
        this.tempCoords = new Vector2();
        this.pointerOverActors = new Actor[20];
        this.pointerTouched = new boolean[20];
        this.pointerScreenX = new int[20];
        this.pointerScreenY = new int[20];
        this.touchFocuses = new SnapshotArray(true, 4, Stage$TouchFocus.class);
        this.actionsRequestRendering = true;
        this.debugTableUnderMouse = Debug.none;
        this.debugColor = new Color(0.0f, 1.0f, 0.0f, 0.85f);
        if (viewport == null) {
            throw new IllegalArgumentException("viewport cannot be null.");
        } else if (batch == null) {
            throw new IllegalArgumentException("batch cannot be null.");
        } else {
            this.viewport = viewport;
            this.batch = batch;
            this.root = new Group();
            this.root.setStage(this);
            viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        }
    }

    public void draw() {
        Camera camera = this.viewport.getCamera();
        camera.update();
        if (this.root.isVisible()) {
            Batch batch = this.batch;
            if (batch != null) {
                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                this.root.draw(batch, 1.0f);
                batch.end();
            }
            if (debug) {
                drawDebug();
            }
        }
    }

    private void drawDebug() {
        if (this.debugShapes == null) {
            this.debugShapes = new ShapeRenderer();
            this.debugShapes.setAutoShapeType(true);
        }
        if (!(this.debugUnderMouse || this.debugParentUnderMouse)) {
            if (this.debugTableUnderMouse == Debug.none) {
                if (this.debugAll) {
                    this.root.debugAll();
                }
                Gdx.gl.glEnable(GL20.GL_BLEND);
                this.debugShapes.setProjectionMatrix(this.viewport.getCamera().combined);
                this.debugShapes.begin();
                this.root.drawDebug(this.debugShapes);
                this.debugShapes.end();
            }
        }
        screenToStageCoordinates(this.tempCoords.set((float) Gdx.input.getX(), (float) Gdx.input.getY()));
        Actor actor = hit(this.tempCoords.f16x, this.tempCoords.f17y, true);
        if (actor != null) {
            if (this.debugParentUnderMouse && actor.parent != null) {
                actor = actor.parent;
            }
            if (this.debugTableUnderMouse == Debug.none) {
                actor.setDebug(true);
            } else {
                while (actor != null) {
                    if (actor instanceof Table) {
                        break;
                    }
                    actor = actor.parent;
                }
                if (actor != null) {
                    ((Table) actor).debug(this.debugTableUnderMouse);
                } else {
                    return;
                }
            }
            if (this.debugAll && (actor instanceof Group)) {
                ((Group) actor).debugAll();
            }
            disableDebug(this.root, actor);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            this.debugShapes.setProjectionMatrix(this.viewport.getCamera().combined);
            this.debugShapes.begin();
            this.root.drawDebug(this.debugShapes);
            this.debugShapes.end();
        }
    }

    private void disableDebug(Actor actor, Actor except) {
        if (actor != except) {
            actor.setDebug(false);
            if (actor instanceof Group) {
                SnapshotArray<Actor> children = ((Group) actor).children;
                int n = children.size;
                for (int i = 0; i < n; i++) {
                    disableDebug((Actor) children.get(i), except);
                }
            }
        }
    }

    public void act() {
        act(Math.min(Gdx.graphics.getDeltaTime(), 0.033333335f));
    }

    public void act(float delta) {
        int n = this.pointerOverActors.length;
        for (int pointer = 0; pointer < n; pointer++) {
            Actor overLast = this.pointerOverActors[pointer];
            if (this.pointerTouched[pointer]) {
                this.pointerOverActors[pointer] = fireEnterAndExit(overLast, this.pointerScreenX[pointer], this.pointerScreenY[pointer], pointer);
            } else if (overLast != null) {
                this.pointerOverActors[pointer] = null;
                screenToStageCoordinates(this.tempCoords.set((float) this.pointerScreenX[pointer], (float) this.pointerScreenY[pointer]));
                InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
                event.setType(Type.exit);
                event.setStage(this);
                event.setStageX(this.tempCoords.f16x);
                event.setStageY(this.tempCoords.f17y);
                event.setRelatedActor(overLast);
                event.setPointer(pointer);
                overLast.fire(event);
                Pools.free(event);
            }
        }
        ApplicationType type = Gdx.app.getType();
        if (type == ApplicationType.Desktop || type == ApplicationType.Applet || type == ApplicationType.WebGL) {
            this.mouseOverActor = fireEnterAndExit(this.mouseOverActor, this.mouseScreenX, this.mouseScreenY, -1);
        }
        this.root.act(delta);
    }

    private Actor fireEnterAndExit(Actor overLast, int screenX, int screenY, int pointer) {
        screenToStageCoordinates(this.tempCoords.set((float) screenX, (float) screenY));
        Actor over = hit(this.tempCoords.f16x, this.tempCoords.f17y, true);
        if (over == overLast) {
            return overLast;
        }
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setStageX(this.tempCoords.f16x);
        event.setStageY(this.tempCoords.f17y);
        event.setPointer(pointer);
        if (overLast != null) {
            event.setType(Type.exit);
            event.setRelatedActor(over);
            overLast.fire(event);
        }
        if (over != null) {
            event.setType(Type.enter);
            event.setRelatedActor(overLast);
            over.fire(event);
        }
        Pools.free(event);
        return over;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX >= this.viewport.getScreenX()) {
            if (screenX < this.viewport.getScreenX() + this.viewport.getScreenWidth()) {
                if (Gdx.graphics.getHeight() - screenY >= this.viewport.getScreenY()) {
                    if (Gdx.graphics.getHeight() - screenY < this.viewport.getScreenY() + this.viewport.getScreenHeight()) {
                        this.pointerTouched[pointer] = true;
                        this.pointerScreenX[pointer] = screenX;
                        this.pointerScreenY[pointer] = screenY;
                        screenToStageCoordinates(this.tempCoords.set((float) screenX, (float) screenY));
                        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
                        event.setType(Type.touchDown);
                        event.setStage(this);
                        event.setStageX(this.tempCoords.f16x);
                        event.setStageY(this.tempCoords.f17y);
                        event.setPointer(pointer);
                        event.setButton(button);
                        Actor target = hit(this.tempCoords.f16x, this.tempCoords.f17y, true);
                        if (target != null) {
                            target.fire(event);
                        } else if (this.root.getTouchable() == Touchable.enabled) {
                            this.root.fire(event);
                        }
                        boolean handled = event.isHandled();
                        Pools.free(event);
                        return handled;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        this.pointerScreenX[pointer] = screenX;
        this.pointerScreenY[pointer] = screenY;
        this.mouseScreenX = screenX;
        this.mouseScreenY = screenY;
        if (this.touchFocuses.size == 0) {
            return false;
        }
        screenToStageCoordinates(this.tempCoords.set((float) screenX, (float) screenY));
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setType(Type.touchDragged);
        event.setStage(this);
        event.setStageX(this.tempCoords.f16x);
        event.setStageY(this.tempCoords.f17y);
        event.setPointer(pointer);
        SnapshotArray<Stage$TouchFocus> touchFocuses = this.touchFocuses;
        Stage$TouchFocus[] focuses = (Stage$TouchFocus[]) touchFocuses.begin();
        int n = touchFocuses.size;
        for (int i = 0; i < n; i++) {
            Stage$TouchFocus focus = focuses[i];
            if (focus.pointer == pointer) {
                if (touchFocuses.contains(focus, true)) {
                    event.setTarget(focus.target);
                    event.setListenerActor(focus.listenerActor);
                    if (focus.listener.handle(event)) {
                        event.handle();
                    }
                }
            }
        }
        touchFocuses.end();
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.pointerTouched[pointer] = false;
        this.pointerScreenX[pointer] = screenX;
        this.pointerScreenY[pointer] = screenY;
        if (this.touchFocuses.size == 0) {
            return false;
        }
        screenToStageCoordinates(this.tempCoords.set((float) screenX, (float) screenY));
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setType(Type.touchUp);
        event.setStage(this);
        event.setStageX(this.tempCoords.f16x);
        event.setStageY(this.tempCoords.f17y);
        event.setPointer(pointer);
        event.setButton(button);
        SnapshotArray<Stage$TouchFocus> touchFocuses = this.touchFocuses;
        Stage$TouchFocus[] focuses = (Stage$TouchFocus[]) touchFocuses.begin();
        int n = touchFocuses.size;
        for (int i = 0; i < n; i++) {
            Stage$TouchFocus focus = focuses[i];
            if (focus.pointer == pointer) {
                if (focus.button == button) {
                    if (touchFocuses.removeValue(focus, true)) {
                        event.setTarget(focus.target);
                        event.setListenerActor(focus.listenerActor);
                        if (focus.listener.handle(event)) {
                            event.handle();
                        }
                        Pools.free(focus);
                    }
                }
            }
        }
        touchFocuses.end();
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        if (screenX >= this.viewport.getScreenX()) {
            if (screenX < this.viewport.getScreenX() + this.viewport.getScreenWidth()) {
                if (Gdx.graphics.getHeight() - screenY >= this.viewport.getScreenY()) {
                    if (Gdx.graphics.getHeight() - screenY < this.viewport.getScreenY() + this.viewport.getScreenHeight()) {
                        this.mouseScreenX = screenX;
                        this.mouseScreenY = screenY;
                        screenToStageCoordinates(this.tempCoords.set((float) screenX, (float) screenY));
                        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
                        event.setStage(this);
                        event.setType(Type.mouseMoved);
                        event.setStageX(this.tempCoords.f16x);
                        event.setStageY(this.tempCoords.f17y);
                        Actor target = hit(this.tempCoords.f16x, this.tempCoords.f17y, true);
                        if (target == null) {
                            target = this.root;
                        }
                        target.fire(event);
                        boolean handled = event.isHandled();
                        Pools.free(event);
                        return handled;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public boolean scrolled(int amount) {
        Actor target = this.scrollFocus == null ? this.root : this.scrollFocus;
        screenToStageCoordinates(this.tempCoords.set((float) this.mouseScreenX, (float) this.mouseScreenY));
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.scrolled);
        event.setScrollAmount(amount);
        event.setStageX(this.tempCoords.f16x);
        event.setStageY(this.tempCoords.f17y);
        target.fire(event);
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean keyDown(int keyCode) {
        Actor target = this.keyboardFocus == null ? this.root : this.keyboardFocus;
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.keyDown);
        event.setKeyCode(keyCode);
        target.fire(event);
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean keyUp(int keyCode) {
        Actor target = this.keyboardFocus == null ? this.root : this.keyboardFocus;
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.keyUp);
        event.setKeyCode(keyCode);
        target.fire(event);
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public boolean keyTyped(char character) {
        Actor target = this.keyboardFocus == null ? this.root : this.keyboardFocus;
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.keyTyped);
        event.setCharacter(character);
        target.fire(event);
        boolean handled = event.isHandled();
        Pools.free(event);
        return handled;
    }

    public void addTouchFocus(EventListener listener, Actor listenerActor, Actor target, int pointer, int button) {
        Stage$TouchFocus focus = (Stage$TouchFocus) Pools.obtain(Stage$TouchFocus.class);
        focus.listenerActor = listenerActor;
        focus.target = target;
        focus.listener = listener;
        focus.pointer = pointer;
        focus.button = button;
        this.touchFocuses.add(focus);
    }

    public void removeTouchFocus(EventListener listener, Actor listenerActor, Actor target, int pointer, int button) {
        SnapshotArray<Stage$TouchFocus> touchFocuses = this.touchFocuses;
        for (int i = touchFocuses.size - 1; i >= 0; i--) {
            Stage$TouchFocus focus = (Stage$TouchFocus) touchFocuses.get(i);
            if (focus.listener == listener && focus.listenerActor == listenerActor && focus.target == target && focus.pointer == pointer && focus.button == button) {
                touchFocuses.removeIndex(i);
                Pools.free(focus);
            }
        }
    }

    public void cancelTouchFocus(Actor actor) {
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.touchUp);
        event.setStageX(-2.14748365E9f);
        event.setStageY(-2.14748365E9f);
        SnapshotArray<Stage$TouchFocus> touchFocuses = this.touchFocuses;
        Stage$TouchFocus[] items = (Stage$TouchFocus[]) touchFocuses.begin();
        int n = touchFocuses.size;
        for (int i = 0; i < n; i++) {
            Stage$TouchFocus focus = items[i];
            if (focus.listenerActor == actor) {
                if (touchFocuses.removeValue(focus, true)) {
                    event.setTarget(focus.target);
                    event.setListenerActor(focus.listenerActor);
                    event.setPointer(focus.pointer);
                    event.setButton(focus.button);
                    focus.listener.handle(event);
                }
            }
        }
        touchFocuses.end();
        Pools.free(event);
    }

    public void cancelTouchFocus() {
        cancelTouchFocusExcept(null, null);
    }

    public void cancelTouchFocusExcept(EventListener exceptListener, Actor exceptActor) {
        InputEvent event = (InputEvent) Pools.obtain(InputEvent.class);
        event.setStage(this);
        event.setType(Type.touchUp);
        event.setStageX(-2.14748365E9f);
        event.setStageY(-2.14748365E9f);
        SnapshotArray<Stage$TouchFocus> touchFocuses = this.touchFocuses;
        Stage$TouchFocus[] items = (Stage$TouchFocus[]) touchFocuses.begin();
        int n = touchFocuses.size;
        for (int i = 0; i < n; i++) {
            Stage$TouchFocus focus = items[i];
            if (focus.listener != exceptListener || focus.listenerActor != exceptActor) {
                if (touchFocuses.removeValue(focus, true)) {
                    event.setTarget(focus.target);
                    event.setListenerActor(focus.listenerActor);
                    event.setPointer(focus.pointer);
                    event.setButton(focus.button);
                    focus.listener.handle(event);
                }
            }
        }
        touchFocuses.end();
        Pools.free(event);
    }

    public void addActor(Actor actor) {
        this.root.addActor(actor);
    }

    public void addAction(Action action) {
        this.root.addAction(action);
    }

    public Array<Actor> getActors() {
        return this.root.children;
    }

    public boolean addListener(EventListener listener) {
        return this.root.addListener(listener);
    }

    public boolean removeListener(EventListener listener) {
        return this.root.removeListener(listener);
    }

    public boolean addCaptureListener(EventListener listener) {
        return this.root.addCaptureListener(listener);
    }

    public boolean removeCaptureListener(EventListener listener) {
        return this.root.removeCaptureListener(listener);
    }

    public void clear() {
        unfocusAll();
        this.root.clear();
    }

    public void unfocusAll() {
        this.scrollFocus = null;
        this.keyboardFocus = null;
        cancelTouchFocus();
    }

    public void unfocus(Actor actor) {
        cancelTouchFocus(actor);
        if (this.scrollFocus != null && this.scrollFocus.isDescendantOf(actor)) {
            this.scrollFocus = null;
        }
        if (this.keyboardFocus != null && this.keyboardFocus.isDescendantOf(actor)) {
            this.keyboardFocus = null;
        }
    }

    public void setKeyboardFocus(Actor actor) {
        if (this.keyboardFocus != actor) {
            FocusEvent event = (FocusEvent) Pools.obtain(FocusEvent.class);
            event.setStage(this);
            event.setType(FocusEvent.Type.keyboard);
            Actor oldKeyboardFocus = this.keyboardFocus;
            if (oldKeyboardFocus != null) {
                event.setFocused(false);
                event.setRelatedActor(actor);
                oldKeyboardFocus.fire(event);
            }
            if (!event.isCancelled()) {
                this.keyboardFocus = actor;
                if (actor != null) {
                    event.setFocused(true);
                    event.setRelatedActor(oldKeyboardFocus);
                    actor.fire(event);
                    if (event.isCancelled()) {
                        setKeyboardFocus(oldKeyboardFocus);
                    }
                }
            }
            Pools.free(event);
        }
    }

    public Actor getKeyboardFocus() {
        return this.keyboardFocus;
    }

    public void setScrollFocus(Actor actor) {
        if (this.scrollFocus != actor) {
            FocusEvent event = (FocusEvent) Pools.obtain(FocusEvent.class);
            event.setStage(this);
            event.setType(FocusEvent.Type.scroll);
            Actor oldScrollFocus = this.scrollFocus;
            if (oldScrollFocus != null) {
                event.setFocused(false);
                event.setRelatedActor(actor);
                oldScrollFocus.fire(event);
            }
            if (!event.isCancelled()) {
                this.scrollFocus = actor;
                if (actor != null) {
                    event.setFocused(true);
                    event.setRelatedActor(oldScrollFocus);
                    actor.fire(event);
                    if (event.isCancelled()) {
                        setScrollFocus(oldScrollFocus);
                    }
                }
            }
            Pools.free(event);
        }
    }

    public Actor getScrollFocus() {
        return this.scrollFocus;
    }

    public Batch getBatch() {
        return this.batch;
    }

    public Viewport getViewport() {
        return this.viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public float getWidth() {
        return this.viewport.getWorldWidth();
    }

    public float getHeight() {
        return this.viewport.getWorldHeight();
    }

    public Camera getCamera() {
        return this.viewport.getCamera();
    }

    public Group getRoot() {
        return this.root;
    }

    public Actor hit(float stageX, float stageY, boolean touchable) {
        this.root.parentToLocalCoordinates(this.tempCoords.set(stageX, stageY));
        return this.root.hit(this.tempCoords.f16x, this.tempCoords.f17y, touchable);
    }

    public Vector2 screenToStageCoordinates(Vector2 screenCoords) {
        this.viewport.unproject(screenCoords);
        return screenCoords;
    }

    public Vector2 stageToScreenCoordinates(Vector2 stageCoords) {
        this.viewport.project(stageCoords);
        stageCoords.f17y = ((float) this.viewport.getScreenHeight()) - stageCoords.f17y;
        return stageCoords;
    }

    public Vector2 toScreenCoordinates(Vector2 coords, Matrix4 transformMatrix) {
        return this.viewport.toScreenCoordinates(coords, transformMatrix);
    }

    public void calculateScissors(Rectangle localRect, Rectangle scissorRect) {
        Matrix4 transformMatrix;
        this.viewport.calculateScissors(this.batch.getTransformMatrix(), localRect, scissorRect);
        if (this.debugShapes == null || !this.debugShapes.isDrawing()) {
            transformMatrix = this.batch.getTransformMatrix();
        } else {
            transformMatrix = this.debugShapes.getTransformMatrix();
        }
        this.viewport.calculateScissors(transformMatrix, localRect, scissorRect);
    }

    public void setActionsRequestRendering(boolean actionsRequestRendering) {
        this.actionsRequestRendering = actionsRequestRendering;
    }

    public boolean getActionsRequestRendering() {
        return this.actionsRequestRendering;
    }

    public Color getDebugColor() {
        return this.debugColor;
    }

    public void setDebugInvisible(boolean debugInvisible) {
        this.debugInvisible = debugInvisible;
    }

    public void setDebugAll(boolean debugAll) {
        if (this.debugAll != debugAll) {
            this.debugAll = debugAll;
            if (debugAll) {
                debug = true;
            } else {
                this.root.setDebug(false, true);
            }
        }
    }

    public void setDebugUnderMouse(boolean debugUnderMouse) {
        if (this.debugUnderMouse != debugUnderMouse) {
            this.debugUnderMouse = debugUnderMouse;
            if (debugUnderMouse) {
                debug = true;
            } else {
                this.root.setDebug(false, true);
            }
        }
    }

    public void setDebugParentUnderMouse(boolean debugParentUnderMouse) {
        if (this.debugParentUnderMouse != debugParentUnderMouse) {
            this.debugParentUnderMouse = debugParentUnderMouse;
            if (debugParentUnderMouse) {
                debug = true;
            } else {
                this.root.setDebug(false, true);
            }
        }
    }

    public void setDebugTableUnderMouse(Debug debugTableUnderMouse) {
        if (debugTableUnderMouse == null) {
            debugTableUnderMouse = Debug.none;
        }
        if (this.debugTableUnderMouse != debugTableUnderMouse) {
            this.debugTableUnderMouse = debugTableUnderMouse;
            if (debugTableUnderMouse != Debug.none) {
                debug = true;
            } else {
                this.root.setDebug(false, true);
            }
        }
    }

    public void setDebugTableUnderMouse(boolean debugTableUnderMouse) {
        setDebugTableUnderMouse(debugTableUnderMouse ? Debug.all : Debug.none);
    }

    public void dispose() {
        clear();
        if (this.ownsBatch) {
            this.batch.dispose();
        }
    }
}
