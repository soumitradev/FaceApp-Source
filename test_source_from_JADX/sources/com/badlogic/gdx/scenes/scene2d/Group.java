package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import java.util.Iterator;

public class Group extends Actor implements Cullable {
    private static final Vector2 tmp = new Vector2();
    final SnapshotArray<Actor> children = new SnapshotArray(true, 4, Actor.class);
    private final Matrix4 computedTransform = new Matrix4();
    private Rectangle cullingArea;
    private final Matrix4 oldTransform = new Matrix4();
    boolean transform = true;
    private final Affine2 worldTransform = new Affine2();

    public void act(float delta) {
        super.act(delta);
        Actor[] actors = (Actor[]) this.children.begin();
        int n = this.children.size;
        for (int i = 0; i < n; i++) {
            actors[i].act(delta);
        }
        this.children.end();
    }

    public void draw(Batch batch, float parentAlpha) {
        if (this.transform) {
            applyTransform(batch, computeTransform());
        }
        drawChildren(batch, parentAlpha);
        if (this.transform) {
            resetTransform(batch);
        }
    }

    protected void drawChildren(Batch batch, float parentAlpha) {
        Batch batch2 = batch;
        float parentAlpha2 = this.color.f1a * parentAlpha;
        SnapshotArray<Actor> children = this.children;
        Actor[] actors = (Actor[]) children.begin();
        Rectangle cullingArea = this.cullingArea;
        float cullLeft;
        float cullTop;
        int i;
        float offsetX;
        if (cullingArea != null) {
            cullLeft = cullingArea.f12x;
            float cullRight = cullingArea.width + cullLeft;
            float cullBottom = cullingArea.f13y;
            cullTop = cullingArea.height + cullBottom;
            Rectangle rectangle;
            if (r0.transform) {
                int n = children.size;
                for (i = 0; i < n; i++) {
                    Actor child = actors[i];
                    if (child.isVisible()) {
                        float cx = child.f7x;
                        float cy = child.f8y;
                        if (cx <= cullRight && cy <= cullTop && child.width + cx >= cullLeft && child.height + cy >= cullBottom) {
                            child.draw(batch2, parentAlpha2);
                        }
                    }
                }
                rectangle = cullingArea;
            } else {
                float cullRight2;
                offsetX = r0.x;
                float offsetY = r0.y;
                r0.x = 0.0f;
                r0.y = 0.0f;
                i = 0;
                int n2 = children.size;
                while (i < n2) {
                    Actor child2 = actors[i];
                    if (child2.isVisible()) {
                        float cx2 = child2.f7x;
                        rectangle = cullingArea;
                        cullingArea = child2.f8y;
                        if (cx2 > cullRight || cullingArea > cullTop) {
                            cullRight2 = cullRight;
                        } else {
                            cullRight2 = cullRight;
                            if (child2.width + cx2 >= cullLeft && child2.height + cullingArea >= cullBottom) {
                                child2.f7x = cx2 + offsetX;
                                child2.f8y = cullingArea + offsetY;
                                child2.draw(batch2, parentAlpha2);
                                child2.f7x = cx2;
                                child2.f8y = cullingArea;
                            }
                        }
                    } else {
                        rectangle = cullingArea;
                        cullRight2 = cullRight;
                    }
                    i++;
                    cullingArea = rectangle;
                    cullRight = cullRight2;
                }
                cullRight2 = cullRight;
                r0.x = offsetX;
                r0.y = offsetY;
            }
        } else {
            if (r0.transform) {
                i = children.size;
                for (int i2 = 0; i2 < i; i2++) {
                    Actor child3 = actors[i2];
                    if (child3.isVisible()) {
                        child3.draw(batch2, parentAlpha2);
                    }
                }
            } else {
                float offsetX2 = r0.x;
                cullLeft = r0.y;
                r0.x = 0.0f;
                r0.y = 0.0f;
                int n3 = children.size;
                for (i = 0; i < n3; i++) {
                    Actor child4 = actors[i];
                    if (child4.isVisible()) {
                        cullTop = child4.f7x;
                        offsetX = child4.f8y;
                        child4.f7x = cullTop + offsetX2;
                        child4.f8y = offsetX + cullLeft;
                        child4.draw(batch2, parentAlpha2);
                        child4.f7x = cullTop;
                        child4.f8y = offsetX;
                    }
                }
                r0.x = offsetX2;
                r0.y = cullLeft;
            }
        }
        children.end();
    }

    public void drawDebug(ShapeRenderer shapes) {
        drawDebugBounds(shapes);
        if (this.transform) {
            applyTransform(shapes, computeTransform());
        }
        drawDebugChildren(shapes);
        if (this.transform) {
            resetTransform(shapes);
        }
    }

    protected void drawDebugChildren(ShapeRenderer shapes) {
        SnapshotArray<Actor> children = this.children;
        Actor[] actors = (Actor[]) children.begin();
        if (this.transform) {
            int n = children.size;
            for (int i = 0; i < n; i++) {
                Actor child = actors[i];
                if (child.isVisible()) {
                    if (child.getDebug() || (child instanceof Group)) {
                        child.drawDebug(shapes);
                    }
                }
            }
            shapes.flush();
        } else {
            float offsetX = this.x;
            float offsetY = this.y;
            this.x = 0.0f;
            this.y = 0.0f;
            int n2 = children.size;
            for (int i2 = 0; i2 < n2; i2++) {
                Actor child2 = actors[i2];
                if (child2.isVisible()) {
                    if (child2.getDebug() || (child2 instanceof Group)) {
                        float cx = child2.f7x;
                        float cy = child2.f8y;
                        child2.f7x = cx + offsetX;
                        child2.f8y = cy + offsetY;
                        child2.drawDebug(shapes);
                        child2.f7x = cx;
                        child2.f8y = cy;
                    }
                }
            }
            this.x = offsetX;
            this.y = offsetY;
        }
        children.end();
    }

    protected Matrix4 computeTransform() {
        Affine2 worldTransform = this.worldTransform;
        float originX = this.originX;
        float originY = this.originY;
        float f = this.x + originX;
        float f2 = this.y + originY;
        worldTransform.setToTrnRotScl(f, f2, this.rotation, this.scaleX, this.scaleY);
        if (!(originX == 0.0f && originY == 0.0f)) {
            worldTransform.translate(-originX, -originY);
        }
        Group parentGroup = this.parent;
        while (parentGroup != null) {
            if (parentGroup.transform) {
                break;
            }
            parentGroup = parentGroup.parent;
        }
        if (parentGroup != null) {
            worldTransform.preMul(parentGroup.worldTransform);
        }
        this.computedTransform.set(worldTransform);
        return this.computedTransform;
    }

    protected void applyTransform(Batch batch, Matrix4 transform) {
        this.oldTransform.set(batch.getTransformMatrix());
        batch.setTransformMatrix(transform);
    }

    protected void resetTransform(Batch batch) {
        batch.setTransformMatrix(this.oldTransform);
    }

    protected void applyTransform(ShapeRenderer shapes, Matrix4 transform) {
        this.oldTransform.set(shapes.getTransformMatrix());
        shapes.setTransformMatrix(transform);
    }

    protected void resetTransform(ShapeRenderer shapes) {
        shapes.setTransformMatrix(this.oldTransform);
    }

    public void setCullingArea(Rectangle cullingArea) {
        this.cullingArea = cullingArea;
    }

    public Rectangle getCullingArea() {
        return this.cullingArea;
    }

    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && getTouchable() == Touchable.disabled) {
            return null;
        }
        Vector2 point = tmp;
        Actor[] childrenArray = this.children.items;
        for (int i = this.children.size - 1; i >= 0; i--) {
            Actor child = childrenArray[i];
            if (child.isVisible()) {
                child.parentToLocalCoordinates(point.set(x, y));
                Actor hit = child.hit(point.f16x, point.f17y, touchable);
                if (hit != null) {
                    return hit;
                }
            }
        }
        return super.hit(x, y, touchable);
    }

    protected void childrenChanged() {
    }

    public void addActor(Actor actor) {
        if (actor.parent != null) {
            actor.parent.removeActor(actor, false);
        }
        this.children.add(actor);
        actor.setParent(this);
        actor.setStage(getStage());
        childrenChanged();
    }

    public void addActorAt(int index, Actor actor) {
        if (actor.parent != null) {
            actor.parent.removeActor(actor, false);
        }
        if (index >= this.children.size) {
            this.children.add(actor);
        } else {
            this.children.insert(index, actor);
        }
        actor.setParent(this);
        actor.setStage(getStage());
        childrenChanged();
    }

    public void addActorBefore(Actor actorBefore, Actor actor) {
        if (actor.parent != null) {
            actor.parent.removeActor(actor, false);
        }
        this.children.insert(this.children.indexOf(actorBefore, true), actor);
        actor.setParent(this);
        actor.setStage(getStage());
        childrenChanged();
    }

    public void addActorAfter(Actor actorAfter, Actor actor) {
        if (actor.parent != null) {
            actor.parent.removeActor(actor, false);
        }
        int index = this.children.indexOf(actorAfter, true);
        if (index == this.children.size) {
            this.children.add(actor);
        } else {
            this.children.insert(index + 1, actor);
        }
        actor.setParent(this);
        actor.setStage(getStage());
        childrenChanged();
    }

    public boolean removeActor(Actor actor) {
        return removeActor(actor, true);
    }

    public boolean removeActor(Actor actor, boolean unfocus) {
        if (!this.children.removeValue(actor, true)) {
            return false;
        }
        if (unfocus) {
            Stage stage = getStage();
            if (stage != null) {
                stage.unfocus(actor);
            }
        }
        actor.setParent(null);
        actor.setStage(null);
        childrenChanged();
        return true;
    }

    public void clearChildren() {
        Actor[] actors = (Actor[]) this.children.begin();
        int n = this.children.size;
        for (int i = 0; i < n; i++) {
            Actor child = actors[i];
            child.setStage(null);
            child.setParent(null);
        }
        this.children.end();
        this.children.clear();
        childrenChanged();
    }

    public void clear() {
        super.clear();
        clearChildren();
    }

    public <T extends Actor> T findActor(String name) {
        int i;
        Array<Actor> children = this.children;
        int n = children.size;
        for (i = 0; i < n; i++) {
            if (name.equals(((Actor) children.get(i)).getName())) {
                return (Actor) children.get(i);
            }
        }
        n = children.size;
        for (i = 0; i < n; i++) {
            Actor child = (Actor) children.get(i);
            if (child instanceof Group) {
                Actor actor = ((Group) child).findActor(name);
                if (actor != null) {
                    return actor;
                }
            }
        }
        return null;
    }

    protected void setStage(Stage stage) {
        super.setStage(stage);
        Actor[] childrenArray = this.children.items;
        int n = this.children.size;
        for (int i = 0; i < n; i++) {
            childrenArray[i].setStage(stage);
        }
    }

    public boolean swapActor(int first, int second) {
        int maxIndex = this.children.size;
        if (first >= 0) {
            if (first < maxIndex) {
                if (second >= 0) {
                    if (second < maxIndex) {
                        this.children.swap(first, second);
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public boolean swapActor(Actor first, Actor second) {
        int firstIndex = this.children.indexOf(first, true);
        int secondIndex = this.children.indexOf(second, true);
        if (firstIndex != -1) {
            if (secondIndex != -1) {
                this.children.swap(firstIndex, secondIndex);
                return true;
            }
        }
        return false;
    }

    public SnapshotArray<Actor> getChildren() {
        return this.children;
    }

    public boolean hasChildren() {
        return this.children.size > 0;
    }

    public void setTransform(boolean transform) {
        this.transform = transform;
    }

    public boolean isTransform() {
        return this.transform;
    }

    public Vector2 localToDescendantCoordinates(Actor descendant, Vector2 localCoords) {
        Group parent = descendant.parent;
        if (parent == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Child is not a descendant: ");
            stringBuilder.append(descendant);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (parent != this) {
            localToDescendantCoordinates(parent, localCoords);
        }
        descendant.parentToLocalCoordinates(localCoords);
        return localCoords;
    }

    public void setDebug(boolean enabled, boolean recursively) {
        setDebug(enabled);
        if (recursively) {
            Iterator i$ = this.children.iterator();
            while (i$.hasNext()) {
                Actor child = (Actor) i$.next();
                if (child instanceof Group) {
                    ((Group) child).setDebug(enabled, recursively);
                } else {
                    child.setDebug(enabled);
                }
            }
        }
    }

    public Group debugAll() {
        setDebug(true, true);
        return this;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(128);
        toString(buffer, 1);
        buffer.setLength(buffer.length() - 1);
        return buffer.toString();
    }

    void toString(StringBuilder buffer, int indent) {
        buffer.append(super.toString());
        buffer.append('\n');
        Actor[] actors = (Actor[]) this.children.begin();
        int n = this.children.size;
        for (int i = 0; i < n; i++) {
            for (int ii = 0; ii < indent; ii++) {
                buffer.append("|  ");
            }
            Actor actor = actors[i];
            if (actor instanceof Group) {
                ((Group) actor).toString(buffer, indent + 1);
            } else {
                buffer.append(actor);
                buffer.append('\n');
            }
        }
        this.children.end();
    }
}
