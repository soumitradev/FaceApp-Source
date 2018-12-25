package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;

public class Touchpad extends Widget {
    private final Circle deadzoneBounds;
    private float deadzoneRadius;
    private final Circle knobBounds;
    private final Vector2 knobPercent;
    private final Vector2 knobPosition;
    boolean resetOnTouchUp;
    private TouchpadStyle style;
    private final Circle touchBounds;
    boolean touched;

    public static class TouchpadStyle {
        public Drawable background;
        public Drawable knob;

        public TouchpadStyle(Drawable background, Drawable knob) {
            this.background = background;
            this.knob = knob;
        }

        public TouchpadStyle(TouchpadStyle style) {
            this.background = style.background;
            this.knob = style.knob;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Touchpad$1 */
    class C12401 extends InputListener {
        C12401() {
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (Touchpad.this.touched) {
                return false;
            }
            Touchpad.this.touched = true;
            Touchpad.this.calculatePositionAndValue(x, y, false);
            return true;
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Touchpad.this.calculatePositionAndValue(x, y, false);
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Touchpad.this.touched = false;
            Touchpad.this.calculatePositionAndValue(x, y, Touchpad.this.resetOnTouchUp);
        }
    }

    public Touchpad(float deadzoneRadius, Skin skin) {
        this(deadzoneRadius, (TouchpadStyle) skin.get(TouchpadStyle.class));
    }

    public Touchpad(float deadzoneRadius, Skin skin, String styleName) {
        this(deadzoneRadius, (TouchpadStyle) skin.get(styleName, TouchpadStyle.class));
    }

    public Touchpad(float deadzoneRadius, TouchpadStyle style) {
        this.resetOnTouchUp = true;
        this.knobBounds = new Circle(0.0f, 0.0f, 0.0f);
        this.touchBounds = new Circle(0.0f, 0.0f, 0.0f);
        this.deadzoneBounds = new Circle(0.0f, 0.0f, 0.0f);
        this.knobPosition = new Vector2();
        this.knobPercent = new Vector2();
        if (deadzoneRadius < 0.0f) {
            throw new IllegalArgumentException("deadzoneRadius must be > 0");
        }
        this.deadzoneRadius = deadzoneRadius;
        this.knobPosition.set(getWidth() / 2.0f, getHeight() / 2.0f);
        setStyle(style);
        setSize(getPrefWidth(), getPrefHeight());
        addListener(new C12401());
    }

    void calculatePositionAndValue(float x, float y, boolean isTouchUp) {
        float oldPositionX = this.knobPosition.f16x;
        float oldPositionY = this.knobPosition.f17y;
        float oldPercentX = this.knobPercent.f16x;
        float oldPercentY = this.knobPercent.f17y;
        float centerX = this.knobBounds.f111x;
        float centerY = this.knobBounds.f112y;
        this.knobPosition.set(centerX, centerY);
        this.knobPercent.set(0.0f, 0.0f);
        if (!(isTouchUp || this.deadzoneBounds.contains(x, y))) {
            this.knobPercent.set((x - centerX) / this.knobBounds.radius, (y - centerY) / this.knobBounds.radius);
            float length = this.knobPercent.len();
            if (length > 1.0f) {
                this.knobPercent.scl(1.0f / length);
            }
            if (this.knobBounds.contains(x, y)) {
                this.knobPosition.set(x, y);
            } else {
                this.knobPosition.set(this.knobPercent).nor().scl(this.knobBounds.radius).add(this.knobBounds.f111x, this.knobBounds.f112y);
            }
        }
        if (oldPercentX != this.knobPercent.f16x || oldPercentY != this.knobPercent.f17y) {
            ChangeEvent changeEvent = (ChangeEvent) Pools.obtain(ChangeEvent.class);
            if (fire(changeEvent)) {
                this.knobPercent.set(oldPercentX, oldPercentY);
                this.knobPosition.set(oldPositionX, oldPositionY);
            }
            Pools.free(changeEvent);
        }
    }

    public void setStyle(TouchpadStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null");
        }
        this.style = style;
        invalidateHierarchy();
    }

    public TouchpadStyle getStyle() {
        return this.style;
    }

    public Actor hit(float x, float y, boolean touchable) {
        return this.touchBounds.contains(x, y) ? this : null;
    }

    public void layout() {
        float halfWidth = getWidth() / 2.0f;
        float halfHeight = getHeight() / 2.0f;
        float radius = Math.min(halfWidth, halfHeight);
        this.touchBounds.set(halfWidth, halfHeight, radius);
        if (this.style.knob != null) {
            radius -= Math.max(this.style.knob.getMinWidth(), this.style.knob.getMinHeight()) / 2.0f;
        }
        this.knobBounds.set(halfWidth, halfHeight, radius);
        this.deadzoneBounds.set(halfWidth, halfHeight, this.deadzoneRadius);
        this.knobPosition.set(halfWidth, halfHeight);
        this.knobPercent.set(0.0f, 0.0f);
    }

    public void draw(Batch batch, float parentAlpha) {
        validate();
        Color c = getColor();
        Batch batch2 = batch;
        batch2.setColor(c.f4r, c.f3g, c.f2b, c.f1a * parentAlpha);
        float x = getX();
        float y = getY();
        float w = getWidth();
        float h = getHeight();
        Drawable bg = this.style.background;
        if (bg != null) {
            bg.draw(batch2, x, y, w, h);
        }
        Drawable knob = r0.style.knob;
        if (knob != null) {
            knob.draw(batch2, x + (r0.knobPosition.f16x - (knob.getMinWidth() / 2.0f)), y + (r0.knobPosition.f17y - (knob.getMinHeight() / 2.0f)), knob.getMinWidth(), knob.getMinHeight());
        }
    }

    public float getPrefWidth() {
        return this.style.background != null ? this.style.background.getMinWidth() : 0.0f;
    }

    public float getPrefHeight() {
        return this.style.background != null ? this.style.background.getMinHeight() : 0.0f;
    }

    public boolean isTouched() {
        return this.touched;
    }

    public boolean getResetOnTouchUp() {
        return this.resetOnTouchUp;
    }

    public void setResetOnTouchUp(boolean reset) {
        this.resetOnTouchUp = reset;
    }

    public void setDeadzone(float deadzoneRadius) {
        if (deadzoneRadius < 0.0f) {
            throw new IllegalArgumentException("deadzoneRadius must be > 0");
        }
        this.deadzoneRadius = deadzoneRadius;
        invalidate();
    }

    public float getKnobX() {
        return this.knobPosition.f16x;
    }

    public float getKnobY() {
        return this.knobPosition.f17y;
    }

    public float getKnobPercentX() {
        return this.knobPercent.f16x;
    }

    public float getKnobPercentY() {
        return this.knobPercent.f17y;
    }
}
